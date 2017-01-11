// This is a project for adding and multiplying integers
// that was inspired by the FracCalc project.  Part of the
// goal of this project is to practice using arrays.
//
// https://docs.google.com/document/d/1EqzXgpcejZrd6V7JezHE7lbpPhdfd56mbaHAJ0BZV5c/edit?ts=58763ce6


package fracCalc;

import java.util.Scanner;

public class ArrayArithmetic {
    public static void main(String[] args) {
        addOrMultiply();
    }

    // -------------------     STEP 1    ---------------------- //
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

    public static void addOrMultiply() {
        int add = 1, mult = 2;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("What operation (" + add + " for addition, "
                + mult + " for multiplication.");
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

    public static final int ADD = 1;
    public static final int MULT = 2;

    public static int getOperation(Scanner sc) {
        while (true) {
            System.out.println("What operation (" + ADD + " for addition, "
                + MULT + " for multiplication.");
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
}
