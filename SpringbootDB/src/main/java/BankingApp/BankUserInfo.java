package com.example.bankingsystemsb;

import java.util.Objects;
import java.util.Scanner;


public class BankingUser {

    static Scanner scan = new Scanner(System.in);
   
    private Integer id;
    private String name;
    private String debitCardNumber;
    private double balance;
    private boolean active = true;

    public BankingUser(String debitCardNumber, String name, Integer id, double balance, boolean active) {
        this.debitCardNumber = debitCardNumber;
        this.name = name;
        this.id = id;
        this.balance = balance;
	this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public boolean isActive() {
	return active;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public String accountDetails() {
        return "Account Number: " + this.debitCardNumber
                + "\nAccount Holder: " + this.name
                + "\nBalance: $" + String.format("%.2f", (double) this.balance);
    }

    static double withDraw(double balance) {
        System.out.print("ENTER AMOUNT TO WITHDRAW: $");
        double amount = scan.nextDouble();
        if (amount > balance) {
            System.out.println("INSUFFICIENT FUNDS, TRY AGAIN");
            return withDraw(balance);
        } else if (amount < 0) {
            System.out.println("INVALID AMOUNT, TRY AGAIN");
            return withDraw(balance);
        } else {
            return amount;
        }
    }

    @Override
    public String toString() {
        return "Account: " + debitCardNumber +
                ", Name: " + name +
                ", ID: " + id +
                ", Balance: $" + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankingUser that = (BankingUser) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(debitCardNumber, that.debitCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, debitCardNumber);

    }

}
