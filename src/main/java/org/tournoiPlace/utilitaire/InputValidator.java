package org.tournoiPlace.utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    // Method for validating strings with a loop for re-entry
    public static String readValidString(String fieldName) {
        String input;
        do {
            System.out.print("Enter " + fieldName + ": ");
            input = scanner.nextLine();
            if (!validateString(input, fieldName)) {
                System.out.println("Invalid input. " + fieldName + " cannot be empty.");
            }
        } while (!validateString(input, fieldName));
        return input;
    }

    // Method for validating positive integers with a loop for re-entry
    public static int readValidPositiveInt(String fieldName) {
        int number;
        while (true) {
            try {
                System.out.print("Enter " + fieldName + ": ");
                number = Integer.parseInt(scanner.nextLine());
                if (validatePositiveInt(number, fieldName)) {
                    return number;
                }
                System.out.println("Invalid input: " + fieldName + " must be a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method for validating positive doubles with a loop for re-entry
    public static double readValidPositiveDouble(String fieldName) {
        double number;
        while (true) {
            try {
                System.out.print("Enter " + fieldName + ": ");
                number = Double.parseDouble(scanner.nextLine());
                if (validatePositiveDouble(number, fieldName)) {
                    return number;
                }
                System.out.println("Invalid input: " + fieldName + " must be a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Method for validating date with a loop for re-entry
    public static LocalDate readValidDate(String fieldName) {
        LocalDate date;
        while (true) {
            System.out.print("Enter " + fieldName + " (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            if (validateDate(dateStr, fieldName)) {
                return LocalDate.parse(dateStr);
            }
            System.out.println("Invalid input: " + fieldName + " must be in the format YYYY-MM-DD.");
        }
    }


    public static boolean validateDifficulty(String difficulty) {
        return difficulty.equalsIgnoreCase("Easy") ||
                difficulty.equalsIgnoreCase("Medium") ||
                difficulty.equalsIgnoreCase("Hard");
    }

    // Helper methods for validation (without looping)
    public static boolean validateString(String input, String fieldName) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean validatePositiveInt(int number, String fieldName) {
        return number > 0;
    }

    public static boolean validatePositiveDouble(double number, String fieldName) {
        return number > 0;
    }

    public static boolean validateDate(String dateStr, String fieldName) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
