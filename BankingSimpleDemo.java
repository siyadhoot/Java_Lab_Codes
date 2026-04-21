/*
    Simple Banking Application (Inheritance Hierarchy Demo)
*/

import java.util.ArrayList;

// Checked exception for business-rule validation
class BankingException extends Exception {
    public BankingException(String message) {
        super(message);
    }
}

// -------------------- Base Class --------------------
class BankAccount {

    private final String accountNo;
    private final String holderName;
    protected double balance;     // accessible in child classes

    public BankAccount(String accountNo, String holderName, double openingBalance) throws BankingException {

        if (accountNo == null || accountNo.isBlank()) {
            throw new BankingException("Account number cannot be empty.");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new BankingException("Holder name cannot be empty.");
        }
        if (openingBalance < 0) {
            throw new BankingException("Opening balance cannot be negative.");
        }

        this.accountNo = accountNo;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    // Common deposit for all accounts
    public void deposit(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    // Generic withdraw rule (can be overridden)
    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }
        if (amount > balance) {
            throw new BankingException("Insufficient balance.");
        }
        balance -= amount;
    }

    // Normal method. Child classes can override.
    public String getAccountType() {
        return "GENERIC";
    }

    public String summary() {
        return String.format("[%s] AccNo=%s, Holder=%s, Balance=%.2f",
                getAccountType(), accountNo, holderName, balance);
    }
}

// -------------------- Child Class 1: Savings --------------------
class SavingsAccount extends BankAccount {

    private final double minBalance;
    private final double interestRate; // yearly %

    public SavingsAccount(String accountNo, String holderName,
                          double openingBalance, double minBalance, double interestRate) throws BankingException {
        super(accountNo, holderName, openingBalance);

        if (minBalance < 0) {
            throw new BankingException("Minimum balance cannot be negative.");
        }
        if (interestRate < 0) {
            throw new BankingException("Interest rate cannot be negative.");
        }

        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }


    public String getAccountType() {
        return "SAVINGS";
    }

    // Override withdraw: must maintain min balance

    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }

        double remaining = balance - amount;
        if (remaining < minBalance) {
            throw new BankingException("Withdrawal denied: minimum balance must be maintained: " + minBalance);
        }

        balance = remaining;
    }


    public void applyAnnualInterest() {
        balance += (balance * interestRate / 100.0);
    }
}

// -------------------- Child Class 2: Current --------------------
class CurrentAccount extends BankAccount {

    private final double overdraftLimit;

    public CurrentAccount(String accountNo, String holderName,
                          double openingBalance, double overdraftLimit) throws BankingException {
        super(accountNo, holderName, openingBalance);

        if (overdraftLimit < 0) {
            throw new BankingException("Overdraft limit cannot be negative.");
        }

        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }

    // Override withdraw: allow negative balance up to overdraft limit

    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }

        double remaining = balance - amount;
        if (remaining < -overdraftLimit) {
            throw new BankingException("Withdrawal denied: overdraft limit exceeded: " + overdraftLimit);
        }

        balance = remaining;
    }
}

// -------------------- Driver / Main --------------------
public class BankingSimpleDemo {

    public static void main(String[] args) {

        try {

            BankAccount acc1 = new SavingsAccount("SA-101", "Ayaan", 5000, 1000, 4.0);
            BankAccount acc2 = new CurrentAccount("CA-201", "Isha", 2000, 3000);
            BankAccount acc3 = new BankAccount("BA-301", "Rahul", 1500);

            ArrayList<BankAccount> accounts = new ArrayList<>();
            accounts.add(acc1);
            accounts.add(acc2);
            accounts.add(acc3);

            System.out.println("---- Initial State ----");
            printAll(accounts);

            System.out.println("\n---- Deposit 1000 in all accounts ----");
            for (BankAccount acc : accounts) {
                acc.deposit(1000);
            }
            printAll(accounts);

            System.out.println("\n---- Withdrawals (shows overriding rules) ----");
            acc1.withdraw(4500);  // savings rule check
            acc2.withdraw(6000);  // overdraft rule check
            acc3.withdraw(500);   // generic rule check

            printAll(accounts);

        } catch (BankingException ex) {
            System.out.println("Banking error: " + ex.getMessage());
        }
    }

    private static void printAll(ArrayList<BankAccount> accounts) {
        for (BankAccount acc : accounts) {
            System.out.println(acc.summary());
        }
    }
}