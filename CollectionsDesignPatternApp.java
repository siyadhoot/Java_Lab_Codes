import java.util.*;

// ─── Product entity ───────────────────────────────────────────────────────────
class Product {
    int    productId;
    String name;
    double price;
    int    quantity;

    Product(int id, String name, double price, int qty) {
        this.productId = id; this.name = name; this.price = price; this.quantity = qty;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %8.2f | %-5d |",
                productId, name, price, quantity);
    }
}

// ─── Singleton ProductManager ─────────────────────────────────────────────────
class ProductManager {
    private static ProductManager instance = null;          // single instance
    private ArrayList<Product>    productList;
    private HashMap<Integer, Product> productMap;           // fast lookup

    private ProductManager() {
        productList = new ArrayList<>();
        productMap  = new HashMap<>();
    }

    // Singleton access method
    public static ProductManager getInstance() {
        if (instance == null) instance = new ProductManager();
        return instance;
    }

    void addProduct(Product p) {
        if (productMap.containsKey(p.productId)) {
            System.out.println("  Product ID already exists!");
            return;
        }
        productList.add(p);
        productMap.put(p.productId, p);
        System.out.println("  Product added: " + p.name);
    }

    void removeProduct(int id) {
        Product p = productMap.remove(id);
        if (p != null) { productList.remove(p); System.out.println("  Removed: " + p.name); }
        else System.out.println("  Product ID not found.");
    }

    void displayProducts() {
        if (productList.isEmpty()) { System.out.println("  No products."); return; }
        System.out.println("\n  +------+----------------------+----------+-------+");
        System.out.println("  | ID   | Name                 |  Price   | Qty   |");
        System.out.println("  +------+----------------------+----------+-------+");
        for (Product p : productList) System.out.println("  " + p);
        System.out.println("  +------+----------------------+----------+-------+");
    }

    void searchProduct(String keyword) {
        boolean found = false;
        for (Product p : productList) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("  Found: " + p); found = true;
            }
        }
        if (!found) System.out.println("  No product matched: " + keyword);
    }

    void sortByPrice() {
        productList.sort(Comparator.comparingDouble(p -> p.price));
        System.out.println("  Products sorted by price.");
    }
}

// ─── Main ─────────────────────────────────────────────────────────────────────
public class CollectionsDesignPatternApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Both references point to same Singleton instance
        ProductManager pm1 = ProductManager.getInstance();
        ProductManager pm2 = ProductManager.getInstance();
        System.out.println("Same instance? " + (pm1 == pm2));  // true

        // Seed data
        pm1.addProduct(new Product(1, "Laptop",    65000, 10));
        pm1.addProduct(new Product(2, "Mouse",       800, 50));
        pm1.addProduct(new Product(3, "Keyboard",   1500, 30));
        pm1.addProduct(new Product(4, "Monitor",   18000,  8));

        while (true) {
            System.out.println("\n1.Add  2.Remove  3.Display  4.Search  5.SortByPrice  0.Exit");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid!"); continue; }

            switch (ch) {
                case 1:
                    try {
                        System.out.print("ID: ");    int id  = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: ");  String nm = sc.nextLine().trim();
                        System.out.print("Price: "); double pr = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Qty: ");   int qty = Integer.parseInt(sc.nextLine().trim());
                        pm1.addProduct(new Product(id, nm, pr, qty));
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                case 2:
                    System.out.print("Product ID to remove: ");
                    try { pm1.removeProduct(Integer.parseInt(sc.nextLine().trim())); }
                    catch (NumberFormatException e) { System.out.println("Invalid!"); }
                    break;
                case 3: pm1.displayProducts(); break;
                case 4:
                    System.out.print("Search keyword: ");
                    pm1.searchProduct(sc.nextLine().trim());
                    break;
                case 5: pm1.sortByPrice(); pm1.displayProducts(); break;
                case 0: System.out.println("Goodbye!"); sc.close(); return;
                default: System.out.println("Invalid option.");
            }
        }
    }
}