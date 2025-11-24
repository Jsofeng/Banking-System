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
}
