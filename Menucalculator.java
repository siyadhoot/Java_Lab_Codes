import java.util.Scanner;

public class Menucalculator {
    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            System.out.println("Cannot divide by zero.");
            return 0;
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter number 1-5.");
                sc.next();
            }

            choice = sc.nextInt();

            if (choice >= 1 && choice <= 4) {
                System.out.print("Enter first number: ");
                double a = sc.nextDouble();
                System.out.print("Enter second number: ");
                double b = sc.nextDouble();

                switch (choice) {
                    case 1:
                        System.out.println("Result = " + add(a, b));
                        break;
                    case 2:
                        System.out.println("Result = " + subtract(a, b));
                        break;
                    case 3:
                        System.out.println("Result = " + multiply(a, b));
                        break;
                    case 4:
                        System.out.println("Result = " + divide(a, b));
                        break;
                }
            } else if (choice != 5) {
                System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}
