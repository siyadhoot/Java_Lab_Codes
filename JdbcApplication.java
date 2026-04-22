import java.sql.*;
import java.util.Scanner;

/**
 * Experiment 9: JDBC Database Application
 * Prerequisites:
 *   - MySQL running locally
 *   - Database: college_db  (CREATE DATABASE college_db;)
 *   - Table created by this program automatically
 *   - mysql-connector-j-*.jar on classpath
 *
 * Compile: javac -cp ".;mysql-connector-j-8.x.x.jar" JdbcApplication.java
 * Run    : java  -cp ".;mysql-connector-j-8.x.x.jar" JdbcApplication
 */
public class JdbcApplication {

    static final String URL  = "jdbc:mysql://localhost:3306/college_db?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "Siya@1003";   // change this

    // ── Get connection ─────────────────────────────────────────────────────────
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    static void createDatabase() {
        String url = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try (Connection con = DriverManager.getConnection(url, USER, PASS);
             Statement st = con.createStatement()) {

            st.executeUpdate("CREATE DATABASE IF NOT EXISTS college_db");
            System.out.println("Database ready.");

        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    // ── Create table ───────────────────────────────────────────────────────────
    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                + "roll_no INT PRIMARY KEY, "
                + "name VARCHAR(50), "
                + "branch VARCHAR(30), "
                + "marks DOUBLE)";
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("  Table ready.");
        } catch (SQLException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Insert record ──────────────────────────────────────────────────────────
    static void insertRecord(int roll, String name, String branch, double marks) {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll); ps.setString(2, name);
            ps.setString(3, branch); ps.setDouble(4, marks);
            ps.executeUpdate();
            System.out.println("  Record inserted.");
        } catch (SQLException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Fetch all records ──────────────────────────────────────────────────────
    static void fetchRecords() {
        String sql = "SELECT * FROM students ORDER BY roll_no";
        try (Connection con = getConnection();
             Statement st   = con.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {
            System.out.println("\n  +------+---------------------+-----------+--------+");
            System.out.println("  | Roll | Name                | Branch    | Marks  |");
            System.out.println("  +------+---------------------+-----------+--------+");
            boolean any = false;
            while (rs.next()) {
                System.out.printf("  | %-4d | %-19s | %-9s | %-6.1f |%n",
                        rs.getInt("roll_no"), rs.getString("name"),
                        rs.getString("branch"), rs.getDouble("marks"));
                any = true;
            }
            if (!any) System.out.println("  No records found.");
            System.out.println("  +------+---------------------+-----------+--------+");
        } catch (SQLException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Update record ──────────────────────────────────────────────────────────
    static void updateRecord(int roll, double newMarks) {
        String sql = "UPDATE students SET marks=? WHERE roll_no=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newMarks); ps.setInt(2, roll);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "  Record updated." : "  Roll No not found.");
        } catch (SQLException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Delete record ──────────────────────────────────────────────────────────
    static void deleteRecord(int roll) {
        String sql = "DELETE FROM students WHERE roll_no=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "  Record deleted." : "  Roll No not found.");
        } catch (SQLException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Main ───────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        createDatabase();
        createTable();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== JDBC Student Database ===");

        while (true) {
            System.out.println("\n1.Insert  2.View  3.Update  4.Delete  0.Exit");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid!"); continue; }

            try {
                switch (ch) {
                    case 1:
                        System.out.print("Roll: ");   int r  = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: ");   String n = sc.nextLine().trim();
                        System.out.print("Branch: "); String br = sc.nextLine().trim();
                        System.out.print("Marks: ");  double m = Double.parseDouble(sc.nextLine().trim());
                        insertRecord(r, n, br, m);
                        break;
                    case 2: fetchRecords(); break;
                    case 3:
                        System.out.print("Roll to update: "); int ur = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("New Marks: ");     double um = Double.parseDouble(sc.nextLine().trim());
                        updateRecord(ur, um);
                        break;
                    case 4:
                        System.out.print("Roll to delete: "); deleteRecord(Integer.parseInt(sc.nextLine().trim()));
                        break;
                    case 0: System.out.println("Goodbye!"); sc.close(); return;
                    default: System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Invalid numeric input.");
            }
        }
    }
}