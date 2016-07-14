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
        return values[2];
    }

    // TODO: Fill in the space below with any helper methods that you think you will need

    public static class MixedFraction {
    	public int whole = 0;
    	public int numerator = 0;
    	public int denominator = 1;

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
        		retval.whole = Integer.parseInt(values[0]);
        		fraction = values[1];
        	} else if (value.contains("/")) {
        		fraction = value;
        	} else {
        		retval.whole = Integer.parseInt(value);
        	}

        	if (fraction != null) {
        		if (fraction.contains("/")) {
        			String values[] = fraction.split("/");
        			retval.numerator = Integer.parseInt(values[0]);
        			retval.denominator = Integer.parseInt(values[1]);
        		}
        	}
        	
    		return retval;
    	}
    	
    	public String description() {
    		return "whole:" + whole + " numerator:" + numerator + " denominator:" + denominator;
    	}

    	public String toString() {
    		if (numerator == 0) {
    			return Integer.toString(whole);
    		} else if (whole == 0) {
    			return "" + numerator + "/" + denominator;
    		} else {
    			return "" + whole + "_" + numerator + "/" + denominator;    			
    		}
    	}
    }



}
