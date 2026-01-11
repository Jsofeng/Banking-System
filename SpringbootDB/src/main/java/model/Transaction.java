package com.example.bankingsystemsb;

import java.time.LocalDate;

public class Transaction {
    private String accountNumber;
    private TransactionType type;
    private double balance;
    private double amount;
    private LocalDate date;

    public Transaction() {}

    public Transaction(String accountNumber, TransactionType type, double balance, double amount, LocalDate date) {
	this.accountNumber = accountNumber;
        this.type = type;
	this.balance = balance;
        this.amount = amount;
        this.date = date;
    }

    public String accountNumber() {
	return accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public double getBalance() {
	return balance;
    }

    public double getAmount() {
	return amount;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}

