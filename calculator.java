import java.util.*;

/**
 * Simple Calculator
 * A command-line arithmetic tool covering the four core operations
 * with user-driven input.
 *
 * Java concepts applied:
 *  - switch-case for operation branching
 *  - static methods for modular design
 *  - conditionals and loops for validation and repeat execution
 *  - try-catch for ArithmeticException and InputMismatchException
 *  - Scanner and System.out for console I/O
 */
public class calculator {

    // ---------- Arithmetic methods ----------

    static double add(double a, double b) {
        return a + b;
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }

    static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    // ---------- Helper methods ----------

    static void showMenu() {
        System.out.println("\n===== Simple Calculator =====");
        System.out.println("  1. Addition       (+)");
        System.out.println("  2. Subtraction    (-)");
        System.out.println("  3. Multiplication (x)");
        System.out.println("  4. Division       (/)");
        System.out.println("  5. Exit");
        System.out.println("=============================");
    }

    static double readNumber(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // discard the bad token
            }
        }
    }

    static int readChoice(Scanner sc) {
        while (true) {
            System.out.print("Choose an operation (1-5): ");
            try {
                int choice = sc.nextInt();
                if (choice >= 1 && choice <= 5) {
                    return choice;
                }
                System.out.println("Please choose a number between 1 and 5.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.nextLine();
            }
        }
    }

    static boolean askToRepeat(Scanner sc) {
        while (true) {
            System.out.print("\nDo you want to perform another calculation? (y/n): ");
            String answer = sc.next().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    // ---------- Main program ----------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Simple Calculator!");

        while (running) {
            showMenu();
            int choice = readChoice(sc);

            if (choice == 5) {
                break;
            }

            double num1 = readNumber(sc, "Enter first number : ");
            double num2 = readNumber(sc, "Enter second number: ");

            try {
                double result;
                String symbol;

                switch (choice) {
                    case 1:
                        result = add(num1, num2);
                        symbol = "+";
                        break;
                    case 2:
                        result = subtract(num1, num2);
                        symbol = "-";
                        break;
                    case 3:
                        result = multiply(num1, num2);
                        symbol = "x";
                        break;
                    case 4:
                        result = divide(num1, num2);
                        symbol = "/";
                        break;
                    default:
                        System.out.println("Unknown operation.");
                        continue;
                }

                System.out.println("\nResult: " + num1 + " " + symbol + " " + num2 + " = " + result);

            } catch (ArithmeticException e) {
                System.out.println("\nError: " + e.getMessage());
            }

            running = askToRepeat(sc);
        }

        System.out.println("\nThank you for using the Simple Calculator. Goodbye!");
        sc.close();
    }
}