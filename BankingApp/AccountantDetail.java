package BankingApp;
import java.util.*;
public class AccountantDetail {
    private String Account_num;
    private String Accountant_Name ;
    private String password;
    // private String Accountant_Email ;
    private double balance = 0.0;
    private String account_Type ;
    private List<String> transtionHistroy;

    
    AccountantDetail(  String name , String account_Type , String password){
        this.Account_num = generateUniqueAccountNumber();
        this.Accountant_Name = name;
        this.password = password ;
        // this.Accountant_Email = email ;
        this.account_Type = account_Type;
        this.transtionHistroy = new ArrayList<>();
        addTranstion("Account create");
    }
   
    public String getAccountNum(){
        return Account_num ;
    }
    public String getName(){
        return this.Accountant_Name ;
    }
    public double getBalance(){
        return balance;
    }
    public String getaccount_Type(){
        return account_Type;
    }
    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        for (String transaction : transtionHistroy) {
            System.out.println(transaction);
        }
    }
    public String getPassword(){
        return password;
    }

    public void deposit(double amount){
        if(amount>0){
            balance += amount;
            addTranstion("Deposited: $" + amount);
            System.out.println("₹" + amount + " deposited successfully!");
        }
        else
            System.out.println("invalid amount");    
    }

    public void withdraw(double amount){
        if( 0 < amount &&  amount <= balance ){
            balance -= amount;
            addTranstion("Withdrawn: $" + amount);
            System.out.println("₹" + amount + " withdrawn successfully!");
        }
        else{
            System.out.println("invalid amount");
        }
    }
    public void addTranstion(String msg){
        transtionHistroy.add(msg + "(Balance : $ "+ balance +")");
    }

    public String generateUniqueAccountNumber(){
        StringBuilder accountNumberBuilder;
        Random random = new Random();
        do {
            accountNumberBuilder = new StringBuilder();
            for (int i = 0; i < 12; i++) {
                accountNumberBuilder.append(random.nextInt(10)); // Random digit (0-9)
            }
        } while (BankingApp.isAccountNumberUsed(accountNumberBuilder.toString())); // Ensure uniqueness
        return accountNumberBuilder.toString();
    }
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
   
}
