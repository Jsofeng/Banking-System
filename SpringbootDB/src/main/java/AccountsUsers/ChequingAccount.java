package com.example.springbootdb.BankingApp;

import FileManager.FileManager;

class ChequingAccount extends AccountType {
    ChequingAccount(String accountNumber, String accountHolder) {
        super(accountNumber, accountHolder, balance); 
    }

    @Override
    void deposit(double amount) {
        balance += amount;
        System.out.printf("[%s] Chequing Deposit: $%.2f%n", accountHolder, amount);
    }

    @Override
    void withdraw(double amount) {
        balance -= amount;
        System.out.printf("[%s] Chequing Withdrawal: $%.2f%n", accountHolder, amount);
    }

    @Override
    void eTransfer(AccountType toAccount, double transferAmount) {
        balance -= transferAmount;
        toAccount.balance += transferAmount;
        System.out.printf("[%s] transferred $%.2f to [%s]%n", this.accountNumber, transferAmount, toAccount.accountNumber);
        System.out.printf("[%s] NEW BALANCE: $%.2f%n[%s] NEW BALANCE: $%.2f%n",
                this.accountNumber, balance, toAccount.accountNumber, toAccount.balance);

        FileManager.logTransaction(this, toAccount, transferAmount);
    }

    @Override
    pubic double getBalance() {
	return this.balance;
}

 public double loan(double balance) {


	double loanAmount = 0;
	if (balance >= 10000) {
            System.out.println("YOU ARE ELIGIBLE FOR A LOAN");
            System.out.print("PLEASE ENTER AMOUNT TO LOAN: $ ");
            Scanner scan = new Scanner(System.in);
            double loanAmount = scan.nextDouble();

            while (loanAmount >= balance) {
                System.out.println("LOAN AMOUNT EXCEEDS BALANCE, PLEASE TRY AGAIN");
                loanAmount = scan.nextDouble();
            }

            if (loanAmount < balance) {
                System.out.println("LOAN PROCESSING...");
                System.out.printf("LOAN APPROVED! YOU HAVE BEEN LOANED: $" + "%.2f\n", loanAmount);
                System.out.println();
                System.out.println("PLEASE READ THE TERMS AND CONDITIONS BELOW");
                System.out.println("PLEASE NOTE: LOAN MUST BE REPAID WITHIN 12 MONTHS WITH A 5% INTEREST RATE");
                System.out.println();
                System.out.printf("BALANCE: $: " + "%.2f\n", balance);
                System.out.printf("LOAN AMOUNT: $" + "%.2f\n", loanAmount);

            }
        } else {
            System.out.println("YOU ARE NOT ELIGIBLE FOR A LOAN");
            System.out.println("SUFFICIENT FUNDS: " + (10000.00 - balance) + " MORE TO QUALIFY");
        }
    	return loanAmount;
    }

    public void eTransfer(AccountType fromAccount, AccountType toAccount, double transferAmount) {
        toAccount.balance += transferAmount;
        fromAccount.balance -= transferAmount;
        System.out.printf("[%s] transferred $%.2f to [%s]%n", this.accountNumber, transferAmount, toAccount.accountNumber);
        System.out.printf("[%s] NEW BALANCE: $%.2f%n[%s] NEW BALANCE $%.2f%n", this.accountNumber, balance, toAccount.accountNumber, toAccount.balance);

        FileManager.eTransaction(this, toAccount, transferAmount);
    }



    private boolean hasLoan = false;

    public boolean hasLoan() {
        return hasLoan;
    }

    public void setHasLoan(boolean hasLoan) {
        this.hasLoan = hasLoan;
    }
}
