import java.io.*;
import java.util.*;

public class StudentRecordManager {
    static final String FILE_NAME = "students.txt";
    static Scanner sc = new Scanner(System.in);

    // ── Add student ────────────────────────────────────────────────────────────
    static void addRecord() {
        try {
            System.out.print("  Roll No : "); int roll = Integer.parseInt(sc.nextLine().trim());
            System.out.print("  Name    : "); String name = sc.nextLine().trim();
            System.out.print("  Branch  : "); String branch = sc.nextLine().trim();
            System.out.print("  Marks   : "); double marks = Double.parseDouble(sc.nextLine().trim());

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bw.write(roll + "," + name + "," + branch + "," + marks);
            bw.newLine();
            bw.close();
            System.out.println("  Record added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Invalid input.");
        } catch (IOException e) {
            System.out.println("  [ERROR] File error: " + e.getMessage());
        }
    }

    // ── View all records ───────────────────────────────────────────────────────
    static void viewRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n  +------+---------------------+-----------+--------+");
            System.out.println("  | Roll | Name                | Branch    | Marks  |");
            System.out.println("  +------+---------------------+-----------+--------+");
            boolean any = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    System.out.printf("  | %-4s | %-19s | %-9s | %-6s |%n",
                            parts[0], parts[1], parts[2], parts[3]);
                    any = true;
                }
            }
            if (!any) System.out.println("  No records found.");
            System.out.println("  +------+---------------------+-----------+--------+");
        } catch (FileNotFoundException e) {
            System.out.println("  No records file found. Add a student first.");
        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Update student record ─────────────────────────────────────────────────
    static void updateRecord(int rollNo) {
        try {
            File file = new File(FILE_NAME);
            List<String> lines = new ArrayList<>();
            boolean found = false;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 4 && Integer.parseInt(p[0]) == rollNo) {
                    System.out.print("  New Name   : "); String name   = sc.nextLine().trim();
                    System.out.print("  New Branch : "); String branch = sc.nextLine().trim();
                    System.out.print("  New Marks  : "); double marks  = Double.parseDouble(sc.nextLine().trim());
                    lines.add(rollNo + "," + name + "," + branch + "," + marks);
                    found = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();
            if (found) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (String l : lines) { bw.write(l); bw.newLine(); }
                bw.close();
                System.out.println("  Record updated.");
            } else {
                System.out.println("  Roll No " + rollNo + " not found.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ── Delete student record ─────────────────────────────────────────────────
    static void deleteRecord(int rollNo) {
        try {
            File file = new File(FILE_NAME);
            List<String> lines = new ArrayList<>();
            boolean found = false;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 4 && Integer.parseInt(p[0]) == rollNo) { found = true; }
                else lines.add(line);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String l : lines) { bw.write(l); bw.newLine(); }
            bw.close();
            System.out.println(found ? "  Record deleted." : "  Roll No not found.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Student Record Manager ===");
        while (true) {
            System.out.println("\n1.Add  2.View  3.Update  4.Delete  0.Exit");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid!"); continue; }
            switch (ch) {
                case 1: addRecord(); break;
                case 2: viewRecords(); break;
                case 3:
                    System.out.print("  Roll No to update: ");
                    try { updateRecord(Integer.parseInt(sc.nextLine().trim())); }
                    catch (NumberFormatException e) { System.out.println("Invalid!"); }
                    break;
                case 4:
                    System.out.print("  Roll No to delete: ");
                    try { deleteRecord(Integer.parseInt(sc.nextLine().trim())); }
                    catch (NumberFormatException e) { System.out.println("Invalid!"); }
                    break;
                case 0: System.out.println("Goodbye!"); sc.close(); return;
                default: System.out.println("Invalid option.");
            }
        }
    }
}