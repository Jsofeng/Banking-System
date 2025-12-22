package com.example.bankingsystemsb;

import java.time.LocalDate;

public class Transaction {
    private TransactionType type;
    private double amount;
    private LocalDate date;

    public Transaction() {}

    public Transaction(TransactionType type, double amount, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
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

