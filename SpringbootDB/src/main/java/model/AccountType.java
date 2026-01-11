package com.example.bankingsystemsb.model;

public abstract class AccountType {

    public String accountNumber;
    public String accountHolder;
    public double balance;

    public AccountType(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    abstract void deposit(double amount);
    abstract void withdraw(double amount);
    abstract void eTransfer(AccountType account, double amount);

    @Override
    public String toString() {
        return "AccountNumber: " + accountNumber +
                ", AccountHolder: " + accountHolder +
                ", Balance: $" + String.format("%.2f", balance);
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
