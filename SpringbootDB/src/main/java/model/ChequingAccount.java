package com.example.bankingsystemsb.model;

import FileManager.FileManager;

public class ChequingAccount extends AccountType {

    public ChequingAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.printf("[%s] Chequing Deposit: $%.2f%n", accountHolder, amount);
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
        System.out.printf("[%s] Chequing Withdrawal: $%.2f%n", accountHolder, amount);
    }

    @Override
    public void eTransfer(AccountType toAccount, double transferAmount) {
        balance -= transferAmount;
        toAccount.balance += transferAmount;

        FileManager.logTransaction(this, toAccount, transferAmount);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    private boolean hasLoan = false;

    public boolean hasLoan() {
        return hasLoan;
    }

    public void setHasLoan(boolean hasLoan) {
        this.hasLoan = hasLoan;
    }
}
