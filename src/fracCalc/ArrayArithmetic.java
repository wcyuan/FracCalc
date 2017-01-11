// This is a project for adding and multiplying integers
// that was inspired by the FracCalc project.  Part of the
// goal of this project is to practice using arrays.
//
// https://docs.google.com/document/d/1EqzXgpcejZrd6V7JezHE7lbpPhdfd56mbaHAJ0BZV5c/edit?ts=58763ce6


package fracCalc;

import java.util.Scanner;

public class ArrayArithmetic {
    public static void main(String[] args) {
        addOrMultiplyFractions();
    }

    // -------------------     STEP 1    ---------------------- //
    // Ask for numbers and print the sum

    public static void alwaysAdd() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("How many numbers?");
            int num_values = sc.nextInt();
            if (num_values == 0) {
                break;
            }
            int[] arr = new int[num_values];
            for (int ii = 0; ii < num_values; ii++) {
                System.out.println("What is number " + (ii + 1) + "?");
                arr[ii] = sc.nextInt();
            }
            int sum = 0;
            for (int value : arr) {
                sum += value;
            }
            System.out.println("Sum = " + sum);
        }
        sc.close();
    }

    // -------------------     STEP 2    ---------------------- //
    // Add multiplication

    public static void addOrMultiply() {
        int add = 1, mult = 2;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("What operation (" + add + " for addition, "
                + mult + " for multiplication).");
            int operation = sc.nextInt();
            if (operation != add && operation != mult) {
                System.out.println("Invalid operation, should be " + add
                    + " for " + "addition or " + mult + " for multiplication");
                continue;
            }
            System.out.println("How many numbers?");
            int num_values = sc.nextInt();
            if (num_values == 0) {
                break;
            }
            int[] arr = new int[num_values];
            for (int ii = 0; ii < num_values; ii++) {
                System.out.println("What is number " + (ii + 1) + "?");
                arr[ii] = sc.nextInt();
            }
            int total = 0;
            if (operation == add) {
                total = 0;
            } else {
                total = 1;
            }
            for (int value : arr) {
                if (operation == add) {
                    total += value;
                } else {
                    total *= value;
                }
            }
            System.out.println("Total = " + total);
        }
        sc.close();
    }

    // -------------------     STEP 3    ---------------------- //
    // Split the logic into functions

    public static final int ADD = 1;
    public static final int MULT = 2;

    public static int getOperation(Scanner sc) {
        while (true) {
            System.out.println("What operation (" + ADD + " for addition, "
                + MULT + " for multiplication.)");
            int operation = sc.nextInt();
            if (operation != ADD && operation != MULT) {
                System.out.println("Invalid operation, should be " + ADD
                    + " for " + "addition or " + MULT + " for multiplication");
            } else {
                return operation;
            }
        }
    }

    public static int[] readValues(Scanner sc) {
        System.out.println("How many numbers?");
        int num_values = sc.nextInt();
        int[] arr = new int[num_values];
        for (int ii = 0; ii < num_values; ii++) {
            System.out.println("What is number " + (ii + 1) + "?");
            arr[ii] = sc.nextInt();
        }
        return arr;
    }

    public static int addArray(int[] arr) {
        int total = 0;
        for (int value : arr) {
            total += value;
        }
        return total;
    }

    public static int multiplyArray(int[] arr) {
        int total = 1;
        for (int value : arr) {
            total *= value;
        }
        return total;
    }

    public static void addOrMultiply2() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int operation = getOperation(sc);
            int[] arr = readValues(sc);
            if (arr.length == 0) {
                break;
            }
            int total;
            if (operation == ADD) {
                total = addArray(arr);
            } else {
                total = multiplyArray(arr);
            }
            System.out.println("Total = " + total);
        }
        sc.close();
    }
    
    // -------------------     STEP 4    ---------------------- //
    // Work on fractions

    public static String[] readFracValues(Scanner sc) {
        System.out.println("How many fractions?");
        int num_values = sc.nextInt();
        String[] arr = new String[num_values];
        for (int ii = 0; ii < num_values; ii++) {
            System.out.println("What is fraction " + (ii + 1) + "?");
            arr[ii] = sc.next();
        }
        return arr;
    }

    public static int getFractionNumerator(String fraction) {
        if (fraction.contains("/")) {
            String values[] = fraction.split("/");
            return Integer.parseInt(values[0]);
        } else {
            return Integer.parseInt(fraction);
        }
    }

    public static int getFractionDenominator(String fraction) {
        if (fraction.contains("/")) {
            String values[] = fraction.split("/");
            return Integer.parseInt(values[1]);
        } else {
            return 1;
        }
    }

    /**
     * Euler's GCD algorithm
     * (https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm)
     */
    public static int gcd(int a, int b) {
        while (true) {
            if (b == 0) {
                return a;
            } else {
                int temp = a % b;
                a = b;
                b = temp;
            }
        }
    }

    public static String reduce(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        if (gcd < 0) {
            gcd *= -1;
        }
        numerator /= gcd;
        denominator /= gcd;
        return numerator + "/" + denominator;
    }
    
    public static String operateOnFracArray(String[] arr, int operation) {
        int numerator;
        int denominator = 1;
        if (operation == ADD) {
            numerator = 0;
        } else {
            numerator = 1;
        }
        for (String value : arr) {
            int frac_numerator = getFractionNumerator(value);
            int frac_denominator = getFractionDenominator(value);
            if (operation == ADD) {
                numerator *= frac_denominator;
                numerator += frac_numerator * denominator;
                denominator *= frac_denominator;
            } else {
                numerator *= frac_numerator;
                denominator *= frac_denominator;
            }
        }
        return reduce(numerator, denominator);
    }

    public static void addOrMultiplyFractions() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int operation = getOperation(sc);
            String[] arr = readFracValues(sc);
            if (arr.length == 0) {
                break;
            }
            System.out.println("Total = " + operateOnFracArray(arr, operation));
        }
        sc.close();
    }
}
