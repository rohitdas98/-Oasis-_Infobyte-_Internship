package Java_development_project;

import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userID;
    private int pin;
    private Account account;

    public User(String userID, int pin, Account account) {
        this.userID = userID;
        this.pin = pin;
        this.account = account;
    }

    public String getUserID() {
        return userID;
    }

    public int getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account() {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        }
        return false; // Insufficient funds
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient, amount));
        }
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

public class ATM {
    private static User currentUser;

    public static void main(String[] args) {
        // Create user accounts (you can add more users here)
        Account userAccount1 = new Account();
        User user1 = new User("user123", 1234, userAccount1);

        // Initialize ATM with users
        User[] users = { user1 };

        // ATM initialization
        ATM atm = new ATM(users);
        atm.runATM();
    }

    private User[] users;

    public ATM(User[] users) {
        this.users = users;
    }

    public void runATM() {
        System.out.println("Welcome to the ATM!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your User ID: ");
            String userID = scanner.next();
            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            currentUser = authenticateUser(userID, pin);

            if (currentUser != null) {
                performTransactions(scanner);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }

    private User authenticateUser(String userID, int pin) {
        for (User user : users) {
            if (user.getUserID().equals(userID) && user.getPin() == pin) {
                return user;
            }
        }
        return null; // Authentication failed
    }

    private void performTransactions(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewTransactionHistory() {
        ArrayList<Transaction> history = currentUser.getAccount().getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
        }
    }

    private void withdraw(Scanner scanner) {
        System.out.print("Enter the withdrawal amount: $");
        double amount = scanner.nextDouble();
        if (currentUser.getAccount().withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private void deposit(Scanner scanner) {
        System.out.print("Enter the deposit amount: $");
        double amount = scanner.nextDouble();
        currentUser.getAccount().deposit(amount);
        System.out.println("Deposit successful.");
    }

    private void transfer(Scanner scanner) {
        System.out.print("Enter the recipient's User ID: ");
        String recipientID = scanner.next();
        User recipient = authenticateUser(recipientID, 0);
        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }

        System.out.print("Enter the transfer amount: $");
        double amount = scanner.nextDouble();

        currentUser.getAccount().transfer(recipient.getAccount(), amount);
        System.out.println("Transfer successful.");
    }
}
