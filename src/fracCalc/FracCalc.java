package fracCalc;

import java.io.IOException;
import java.util.Scanner;

public class FracCalc {

    public static void main(String[] args) throws IOException 
    {
        // TODO: Read the input from the user and call produceAnswer with an equation
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("quit")) {
                break;
            } else {
                System.out.println(produceAnswer(input));
            }
        }
        sc.close();
    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input)
    { 
        // TODO: Implement this function to produce the solution to the input
        String values[] = input.split(" ");
        String improper = toImproperFraction(values[0]);
		long numerator = getFractionNumerator(improper);
		long denominator = getFractionDenominator(improper);
        for (int ii = 1; ii < values.length; ii += 2) {
            String v2improper = toImproperFraction(values[ii+1]);
    		long v2numerator = getFractionNumerator(v2improper);
    		long v2denominator = getFractionDenominator(v2improper);
        	
        	switch (values[ii]) {
        	case "+":
        		numerator *= v2denominator;
        		numerator += v2numerator * denominator;
        		denominator *= v2denominator;
        		break;
        	case "-":
        		numerator *= v2denominator;
        		numerator -= v2numerator * denominator;
        		denominator *= v2denominator;
        		break;
        	case "*":
        		numerator *= v2numerator;
        		denominator *= v2denominator;
        		break;
        	case "/":
        		numerator *= v2denominator;
        		denominator *= v2numerator;
        		break;
        	default:
        		throw new RuntimeException("Invalid operator: " + values[ii]);
        	}
        }
        return toMixedFraction(reduce(numerator, denominator));
    }

    // TODO: Fill in the space below with any helper methods that you think you will need

    // ------------------------------------------------------------------------------ //
    // Below is a solution if you do not use classes
    // Instead, pass everything around as a string, and have functions
    // that convert from one format to another, or to extract parts of
    // a string

    public static String toImproperFraction(String value) {
		String fraction = null;
    	long whole = 0;
    	long numerator = 0;
    	long denominator = 1;

    	if (value.contains("_")) {
    		String values[] = value.split("_");
    		whole = Long.parseLong(values[0]);
    		fraction = values[1];
    	} else if (value.contains("/")) {
    		fraction = value;
    	} else {
    		whole = Long.parseLong(value);
    	}

    	if (fraction != null) {
    		numerator = getFractionNumerator(fraction);
    		denominator = getFractionDenominator(fraction);
    		if (whole < 0) {
    			numerator *= -1;
    		}
    	}
    	
    	// Convert to an improper fraction
		numerator = denominator * whole + numerator;
		if (denominator < 0) {
			denominator *= -1;
			numerator *= -1;
		}
    	return makeFractionString(numerator, denominator);
    }

    public static String makeFractionString(long numerator, long denominator) {
    	return numerator + "/" + denominator;
    }
    
    public static long getFractionNumerator(String fraction) {
		if (fraction.contains("/")) {
			String values[] = fraction.split("/");
			return Long.parseLong(values[0]);
		} else {
			return Long.parseLong(fraction);
		}
    }

    public static long getFractionDenominator(String fraction) {
		if (fraction.contains("/")) {
			String values[] = fraction.split("/");
			return Long.parseLong(values[1]);
		} else {
			return 1;
		}
    }

    public static String toMixedFraction(String fraction) {
		int num_sign = 1, den_sign = 1;
		long numerator = getFractionNumerator(fraction);
		long denominator = getFractionDenominator(fraction);
		if (numerator < 0) {
			num_sign = -1;
			numerator *= -1;
		}
		if (denominator < 0) {
			den_sign = -1;
			denominator *= -1;
		}
		int sign = num_sign * den_sign;

		long whole = numerator / denominator;
		numerator = numerator % denominator;
		whole *= sign;
		numerator *= sign;
		return makeMixedFractionString(whole, numerator, denominator);
    }
    
    public static String makeMixedFractionString(long whole, long numerator, long denominator) {
		if (numerator == 0) {
			return Long.toString(whole);
		} else if (whole == 0) {
			return "" + numerator + "/" + denominator;
		} else {
			if (whole > 0) {
    			return "" + whole + "_" + numerator + "/" + denominator;
			} else {
    			return "" + whole + "_" + -numerator + "/" + denominator;
			}
		}
	}

	public static String reduce(long numerator, long denominator) {
		long gcd = MixedFraction.gcd(numerator, denominator);
		if (gcd < 0) {
			gcd *= -1;
		}
		numerator /= gcd;
		denominator /= gcd;
		return makeFractionString(numerator, denominator);
	}

	// ------------------------------------------------------------------------------ //
    // Below is a solution if you use classes
    //
    public static String produceAnswerWithClasses(String input)
    { 
        // TODO: Implement this function to produce the solution to the input
        String values[] = input.split(" ");
        MixedFraction value = MixedFraction.fromString(values[0]);
        for (int ii = 1; ii < values.length; ii += 2) {
            MixedFraction v2 = MixedFraction.fromString(values[ii+1]);
        	switch (values[ii]) {
        	case "+":
        		value = value.add(v2);
        		break;
        	case "-":
        		value = value.sub(v2);
        		break;
        	case "*":
        		value = value.mult(v2);
        		break;
        	case "/":
        		value = value.div(v2);
        		break;
        	default:
        		throw new RuntimeException("Invalid operator: " + values[ii]);
        	}
        }
        return value.toString();
    }
    
    public static class MixedFraction {
    	
    	// The convention of this class is that the value should
    	// always be (whole + numerator / denominator)
    	// That means for a negative mixed fraction, like -2_1/2, whole must be negative,
    	// and either the numerator or the denominator and numerator should also be negative.
    	// (Preferably the numerator).
    	public long whole = 0;
    	public long numerator = 0;
    	public long denominator = 1;

        /**
         * Parse a single value.  From the spec:
         * 
         * Input values may be in the form of mixed fractions, proper fractions,
         * improper fractions or integers. The integer and fraction parts of a mixed fraction will be
         * separated by an underscore (_) (e.g., “1_3/4” is one and three fourths to distinguish it
         * from “13/4” which is thirteen fourths).
     	 *
     	 * Algorithm:
     	 *   if the string contains a _, split on that into intpart and fracpart.
     	 *   otherwise, if the string contains a /, call it the fracpart, otherwise call it the intpart.
     	 *   
     	 *   if there is a fracpart, split it by / into numerator and denominator, otherwise
     	 *   numerator=0, denominator=1
     	 *
         * @param value
         * @return
         */
    	public static MixedFraction fromString(String value) {
    		MixedFraction retval = new MixedFraction();
    		String fraction = null;
        	if (value.contains("_")) {
        		String values[] = value.split("_");
        		retval.whole = Long.parseLong(values[0]);
        		fraction = values[1];
        	} else if (value.contains("/")) {
        		fraction = value;
        	} else {
        		retval.whole = Long.parseLong(value);
        	}

        	if (fraction != null) {
        		if (fraction.contains("/")) {
        			String values[] = fraction.split("/");
        			retval.numerator = Long.parseLong(values[0]);
        			retval.denominator = Long.parseLong(values[1]);
        			if (retval.whole < 0) {
        				retval.numerator *= -1;
        			}
        		}
        	}
        	
    		return retval;
    	}
    	
    	public String description() {
    		return "whole:" + whole + " numerator:" + numerator + " denominator:" + denominator;
    	}

    	public String toString() {
    		if (numerator == 0) {
    			return Long.toString(whole);
    		} else if (whole == 0) {
    			return "" + numerator + "/" + denominator;
    		} else {
    			if (whole > 0) {
        			return "" + whole + "_" + numerator + "/" + denominator;
    			} else {
        			return "" + whole + "_" + -numerator + "/" + denominator;
    			}
    		}
    	}

    	// In general, the methods below return copies, they don't mutate the object.
    	
    	public MixedFraction copy() {
    		MixedFraction retval = new MixedFraction();
    		retval.whole = whole;
    		retval.numerator = numerator;
    		retval.denominator = denominator;
    		return retval;
    	}
    	
    	public MixedFraction reduced() {
    		MixedFraction retval = copy();
    		long gcd = gcd(numerator, denominator);
    		if (gcd < 0) {
    			gcd *= -1;
    		}
    		retval.numerator /= gcd;
    		retval.denominator /= gcd;
    		return retval;
    	}

    	/**
    	 * Returns a version of the fraction as an improper fraction, where whole is zero.
    	 * This also forces the denominator to be positive.
    	 * This is the form used for most of the calculations.
    	 * Note that this is not necessarily reduced.
    	 * @return
    	 */
    	public MixedFraction improper() {
    		MixedFraction retval = new MixedFraction();
    		retval.denominator = denominator;
			retval.numerator = denominator * whole + numerator;
			if (retval.denominator < 0) {
				retval.denominator *= -1;
				retval.numerator *= -1;
			}
    		return retval;
    	}

    	/**
    	 * Returns a version of the fraction in mixed form.
    	 * This also guarantees that the denominator will be
    	 * positive.  Note that if the number is positive,
    	 * whole, numerator, and denominator will all be positive (or zero).
    	 * If the number is negative, both whole and numerator
    	 * will be negative (or zero).
    	 * Note that it will not necessarily be reduced.
    	 * @return
    	 */
    	public MixedFraction mixed() {
    		MixedFraction retval = new MixedFraction();
    		int whole_sign = 1, num_sign = 1, den_sign = 1;
    		if (whole < 0) {
    			whole_sign = -1;
    			whole *= -1;
    		}
    		if (numerator < 0) {
    			num_sign = -1;
    			numerator *= -1;
    		}
    		if (denominator < 0) {
    			den_sign = -1;
    			denominator *= -1;
    		}
    		int sign = whole_sign * num_sign * den_sign;
    		retval.whole = whole + numerator / denominator;
    		retval.numerator = numerator % denominator;
    		retval.denominator = denominator;
    		retval.whole *= sign;
    		retval.numerator *= sign;
    		return retval;
    	}
    	
    	/**
    	 * Returns the reciprocal of the number, in improper form.
    	 * Also forces the denominator to be positive.
    	 * @return
    	 */
    	public MixedFraction reciprocal() {
    		MixedFraction a = improper();
    		long temp = a.denominator;
    		a.denominator = a.numerator;
    		a.numerator = temp;
			if (a.denominator < 0) {
				a.denominator *= -1;
				a.numerator *= -1;
			}
    		return a;
    	}
    	
    	/**
    	 * Euler's GCD algorithm (https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm)
    	 * 
    	 * @param a
    	 * @param b
    	 * @return
    	 */
    	public static long gcd(long a, long b) {
    		while (true) {
    			if (b == 0) {
    				return a;
    			} else {
    				long temp = a % b;
    				a = b;
    				b = temp;
    			}
    		}
    	}

    	public MixedFraction add(MixedFraction other) {
    		MixedFraction a = improper();
    		other = other.improper();
    		a.numerator *= other.denominator;
    		a.numerator += other.numerator * a.denominator;
    		a.denominator *= other.denominator;
    		return a.reduced().mixed();
    	}

    	public MixedFraction mult(MixedFraction other) {
    		MixedFraction a = improper();
    		other = other.improper();
    		a.numerator *= other.numerator;
    		a.denominator *= other.denominator;
    		return a.reduced().mixed();
    	}

    	public MixedFraction div(MixedFraction other) {
    		return mult(other.reciprocal());
    	}

    	public MixedFraction sub(MixedFraction other) {
    		other = other.improper();
    		other.numerator *= -1;
    		return add(other);
    	}
    }



}
