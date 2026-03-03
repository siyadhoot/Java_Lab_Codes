import java.util.Scanner;

class Vehicle {
    String vehicleNumber;
    String brand;
    String type;
    double price;

    // Constructor
    Vehicle(String vehicleNumber, String brand, String type, double price) {
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.type = type;
        this.price = price;
    }

    // Method to display vehicle details
    void display() {
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Brand: " + brand);
        System.out.println("Type: " + type);
        System.out.println("Price: " + price);
        System.out.println("---------------------------");
    }
}

public class VehicleManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vehicles: ");
        int n = sc.nextInt();
        sc.nextLine();  // clear buffer

        Vehicle[] vehicles = new Vehicle[n];

        // Input vehicle details
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Vehicle " + (i + 1));

            System.out.print("Vehicle Number: ");
            String number = sc.nextLine();

            System.out.print("Brand: ");
            String brand = sc.nextLine();

            System.out.print("Type (Car/Bike/Truck): ");
            String type = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();
            sc.nextLine();  // clear buffer

            vehicles[i] = new Vehicle(number, brand, type, price);
        }

        // Display all vehicles
        System.out.println("\n===== VEHICLE DETAILS =====");
        for (int i = 0; i < n; i++) {
            vehicles[i].display();
        }

        // Generate Summary Report
        double totalValue = 0;
        double highestPrice = vehicles[0].price;

        for (int i = 0; i < n; i++) {
            totalValue += vehicles[i].price;
            if (vehicles[i].price > highestPrice) {
                highestPrice = vehicles[i].price;
            }
        }

        System.out.println("\n===== SUMMARY REPORT =====");
        System.out.println("Total Vehicles: " + n);
        System.out.println("Total Value of Vehicles: " + totalValue);
        System.out.println("Highest Priced Vehicle: " + highestPrice);

        sc.close();
    }
}