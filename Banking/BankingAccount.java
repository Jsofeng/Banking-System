import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

public class BankingAccount {
    DateTimeFormatter currentDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static Scanner scan = new Scanner(System.in);
    String accountNumber;
    private String accountHolder;
    double balance;
    private List<String> transactionHistory;

    public BankingAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }
    public String accountDetails() {
        return "Account Number: " + this.accountNumber
                + "\nAccount Holder: " + this.accountHolder
                + "\nBalance: $" + String.format("%.2f", this.balance);
    }
    /**
     * @param transaction the transaction to be recorded
     * Adds the transaction to the transaction history list with a timestamp
     */

    public void recordTransaction(String transaction) {
        String dtf = LocalDateTime.now().format(currentDateTime);
        this.transactionHistory.add(transaction);
        System.out.println("Transaction recorded " + transaction + " on " + dtf);
    }
    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    /**
     * Closes the account by setting the balance to zero and clearing the transaction history
     */

    public void closeAccount() {
        this.balance = 0.0;
        this.transactionHistory.clear();
        System.out.println("Account: " + this.accountNumber + "has been closed" );
    }

    /**
     * Sets the account information after validating the account number format
     * @param accountNumber the account number to be set
     * @param accountHolder the account holder name to be set
     */

    public void setAccountInfo(String accountNumber, String accountHolder) {
        System.out.println("PLEASE ENTER DEBIT CARD ACCOUNT NUMBER: ");
        accountNumber = scan.nextLine();
        while(!accountNumber.matches("\\d{15}")) {
            System.out.println("INVALID ACCOUNT NUMBER. PLEASE TRY AGAIN: ");
            accountNumber = scan.nextLine();
        }

        System.out.println("PLEASE ENTER ACCOUNT HOLDER NAME: ");
        accountHolder = scan.nextLine();
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    /**
     * Transfers funds to another account after validating the target account number and ensuring sufficient balance
     * @param newAccount the account to which funds will be transferred
     * @param amount the amount to be transferred
     */
    public void transferFunds(BankingAccount newAccount, double amount) {
        System.out.println("Which account would you like to transfer to? ");
        String toAccount = scan.nextLine();
        while(!toAccount.matches(newAccount.accountNumber)) {
            System.out.println("Account not found. Please try again.");
            toAccount = scan.nextLine();
        }

        if(toAccount.matches(newAccount.accountNumber)) {
            String transferDAT = LocalDateTime.now().format(currentDateTime);
            System.out.print("How much would you like to transfer? $");
            amount = scan.nextDouble();
            while(amount > balance) {
                System.out.println("Insufficient funds. Please try again.");
                amount = scan.nextDouble();

            }
            this.balance -= amount;
            newAccount.balance += amount;
            System.out.println("Transfer successful. ACC #:" + this.accountNumber + " New balance: $" + String.format("%.2f", this.balance));
            System.out.println(newAccount.accountNumber + " New balance: $" + String.format("%.2f", newAccount.balance) + "Date" + transferDAT);
            System.out.println();
           /* System.out.println("Transfer more? (Y/N)");
            char response = scan.nextLine().toUpperCase().charAt(0);

            if(response == 'Y') {
                transferFunds(newAccount,amount);
            }  else if (response == 'N') { */
                System.out.println("Returning receipt...");
                System.out.println(this.accountNumber + " transferred: $" + String.format("%.2f", amount) + " to " + newAccount.accountNumber
                                   + "\n" + this.accountNumber + ": New balance: $" + String.format("%.2f", this.balance)
                                   + "\n" + newAccount.accountNumber + ": New balance: $" + String.format("%.2f", newAccount.balance));
            }
        }
    }


