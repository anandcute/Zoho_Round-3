package BankingApp;

import java.util.HashMap;
import java.util.Scanner;

public class BankingApp {
    static HashMap<String , AccountantDetail> accounts = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static boolean isAccountNumberUsed(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();
        if (accountHolderName.isBlank()) {
            System.out.println("Account holder name cannot be empty. Please try again.");
            return;
        }

        System.out.print("Enter account type (Savings/Current): ");
        String accountType = scanner.nextLine();
        if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Current")) {
            System.out.println("Invalid account type. Please enter 'Savings' or 'Current'.");
            return;
        }

        System.out.print("Set a password for your account (minimum 6 characters): ");
        String password = scanner.nextLine();
        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long. Please try again.");
            return;
        }

        AccountantDetail newAccount = new AccountantDetail(accountHolderName, accountType, password);
        accounts.put(newAccount.getAccountNum(), newAccount);
        System.out.println("Account created successfully!");
        System.out.println("Your Account Number: " + newAccount.getAccountNum());
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        AccountantDetail account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.verifyPassword(password)) {
                System.out.print("Enter deposit amount: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid amount. Please enter a valid number.");
                    scanner.next(); // Consume invalid input
                    return;
                }
                double amount = scanner.nextDouble();
                account.deposit(amount);
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        AccountantDetail account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.verifyPassword(password)) {
                System.out.print("Enter withdrawal amount: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid amount. Please enter a valid number.");
                    scanner.next(); // Consume invalid input
                    return;
                }
                double amount = scanner.nextDouble();
                account.withdraw(amount);
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        AccountantDetail account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.verifyPassword(password)) {
                System.out.println("Account Holder: " + account.getName());
                System.out.println("Account Type: " + account.getaccount_Type());
                System.out.println("Current Balance: ₹" + account.getBalance());
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        AccountantDetail account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.verifyPassword(password)) {
                account.displayTransactionHistory();
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

   

    public static void main(String[] args){
        boolean flag = true;
        while (true) {
        System.out.println("1.Depoist \n 2. Withdraw \n 3. Check Balance \n 4. PrintDetails \n 5. account creation \n 6.Exit  " );
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the option : ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                deposit();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                createAccount();
                break;    
            case 6:
                System.exit(0);
                // flag = false;
            default :
                System.out.println("Invalid option");
        }
    }
        
    }
    
}
