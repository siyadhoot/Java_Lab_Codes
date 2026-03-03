import java.util.Scanner;

class Vector {
    double x, y, z;
    int dimension;

    // Constructor
    Vector(int dimension, double x, double y, double z) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Addition
    Vector add(Vector v) throws Exception {
        if (this.dimension != v.dimension) {
            throw new Exception("Dimensions must be same for addition!");
        }
        return new Vector(dimension, this.x + v.x, this.y + v.y, this.z + v.z);
    }

    // Subtraction
    Vector subtract(Vector v) throws Exception {
        if (this.dimension != v.dimension) {
            throw new Exception("Dimensions must be same for subtraction!");
        }
        return new Vector(dimension, this.x - v.x, this.y - v.y, this.z - v.z);
    }

    // Dot Product
    double dotProduct(Vector v) throws Exception {
        if (this.dimension != v.dimension) {
            throw new Exception("Dimensions must be same for dot product!");
        }
        return (this.x * v.x + this.y * v.y + this.z * v.z);
    }

    void display() {
        if (dimension == 2)
            System.out.println("(" + x + ", " + y + ")");
        else
            System.out.println("(" + x + ", " + y + ", " + z + ")");
    }
}

public class VectorOperations {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter dimension (2 or 3): ");
            int dim = sc.nextInt();

            if (dim != 2 && dim != 3) {
                throw new Exception("Dimension must be 2 or 3!");
            }

            System.out.println("Enter first vector:");
            double x1 = sc.nextDouble();
            double y1 = sc.nextDouble();
            double z1 = (dim == 3) ? sc.nextDouble() : 0;

            System.out.println("Enter second vector:");
            double x2 = sc.nextDouble();
            double y2 = sc.nextDouble();
            double z2 = (dim == 3) ? sc.nextDouble() : 0;

            Vector v1 = new Vector(dim, x1, y1, z1);
            Vector v2 = new Vector(dim, x2, y2, z2);

            Vector sum = v1.add(v2);
            Vector diff = v1.subtract(v2);
            double dot = v1.dotProduct(v2);

            System.out.print("Addition: ");
            sum.display();

            System.out.print("Subtraction: ");
            diff.display();

            System.out.println("Dot Product: " + dot);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}