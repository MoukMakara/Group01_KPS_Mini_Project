package co.ksga.utils;

import co.ksga.view.BoxBorder;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductValidation {
    static Scanner sc = new Scanner(System.in);

    // Regex patterns
    static String productNameRegex = "^[A-Za-z0-9 ]+$";
    static String productPriceRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
    static String quantityRegex = "^[0-9]+$";
    static String idRegex = "^[0-9]{1,2}+$";

    /**
     * Validates ID input with specific error messages for each scenario.
     */
    public static int idValidation(String fieldName) {
        while (true) {
            System.out.print("Enter the " + fieldName + ": ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(BoxBorder.red + fieldName + " cannot be empty! Please try again." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches("[0-9]*", input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Text and special characters are not allowed." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches(idRegex, input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Only numeric IDs with 1 or 2 digits are allowed." + BoxBorder.reset);
                continue;
            }

            int id = Integer.parseInt(input);

            return id;
        }
    }

    /**
     * Validates product name input.
     */
    public static String productNameValidation(String fieldName) {
        while (true) {
            System.out.print("Enter the " + fieldName + ": ");
            String input = sc.nextLine().trim(); // Read user input and trim whitespace

            if (input.isEmpty()) {
                System.out.println(BoxBorder.red + fieldName + " cannot be empty! Please try again." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches(productNameRegex, input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Only letters, numbers, and spaces are allowed." + BoxBorder.reset);
                continue;
            }

            return input;
        }
    }


    /**
     * Validates product price
     */
    public static String productPriceValidation(String fieldName) {
        while (true) {
            System.out.print("Enter the " + fieldName + ": ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(BoxBorder.red+ fieldName + " cannot be empty! Please try again." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches("[0-9\\.]*", input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Text and special characters are not allowed." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches(productPriceRegex, input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Only positive numbers with up to 2 decimal places are allowed " + BoxBorder.reset);
                continue;
            }

            double price = Double.parseDouble(input);

            if (price < 0) {
                System.out.println(BoxBorder.red + fieldName + " cannot be less than 0! Please enter a valid positive number." + BoxBorder.reset);
                continue;
            }

            if (price > 100000) {
                System.out.println(BoxBorder.red + fieldName + " cannot be greater than 100,000! Please enter a valid number." + BoxBorder.reset);
                continue;
            }

            return input;
        }
    }
    /**
     * Validates quantity input.
     */
    public static int productQuantityValidation(String fieldName) {
        while (true) {
            System.out.print("Enter the " + fieldName + ": ");
            String input = sc.nextLine().trim(); // Read user input and trim whitespace

            if (input.isEmpty()) {
                System.out.println(BoxBorder.red + fieldName + " cannot be empty! Please try again." + BoxBorder.reset);
                continue;
            }

            if (!Pattern.matches(quantityRegex, input)) {
                System.out.println(BoxBorder.red + "Invalid " + fieldName.toLowerCase() + "! Only positive integers are allowed." + BoxBorder.reset);
                continue;
            }

            int quantity = Integer.parseInt(input);

            if (quantity < 0) {
                System.out.println(BoxBorder.red + fieldName + " cannot be less than 0! Please enter a valid positive number." + BoxBorder.reset);
                continue;
            }

            return quantity;
        }
    }

    public static void main(String[] args) {
        int id = idValidation("id");
    }

}