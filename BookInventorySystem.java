import java.util.ArrayList;
import java.util.Scanner;

// Book Class
class Book {
    String title;
    String author;
    double price;

    // Constructor with exception handling
    Book(String title, String author, double price) throws Exception {
        if (price < 0) {
            throw new Exception("Price cannot be negative!");
        }
        this.title = title;
        this.author = author;
        this.price = price;
    }

    void display() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: " + price);
        System.out.println("-------------------");
    }
}

public class BookInventorySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();

        System.out.print("Enter number of books: ");
        int n = sc.nextInt();
        sc.nextLine(); 

        for (int i = 0; i < n; i++) {

            try {
                System.out.println("\nEnter details of Book " + (i + 1));

                System.out.print("Title: ");
                String title = sc.nextLine();

                System.out.print("Author: ");
                String author = sc.nextLine();

                System.out.print("Price: ");
                double price = sc.nextDouble();
                sc.nextLine();

                Book b = new Book(title, author, price);
                books.add(b);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                i--;  // Retry same book
                sc.nextLine();
            }
        }

        System.out.println("\n===== BOOK INVENTORY =====");
        for (Book b : books) {
            b.display();
        }

        sc.close();
    }
}