package com.example.bankingsystemsb.service;

import com.example.bankingsystemsb.*;
import FileManager.FileManager;
import org.springframework.stereotype.Service;
import com.example.bankingsystemsb.model.BankingUser;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.model.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    /* ================= SUMMARY ================= */

    public List<Transaction> getAccountSummary(String accountNumber) {
        return FileManager.loadTransactions("Transactions.json")
                .stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .toList();
    }

    /* ================= TRANSFER ================= */

    public void transfer(String fromAccount, String toAccount, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        List<BankingUser> users = FileManager.loadUsers("AccountsDB.txt");

        BankingUser sender = null;
        BankingUser receiver = null;

        for (BankingUser u : users) {
            if (u.getDebitCardNumber().equals(fromAccount) && u.isActive()) sender = u;
            if (u.getDebitCardNumber().equals(toAccount) && u.isActive()) receiver = u;
        }

        if (sender == null || receiver == null) {
            throw new NoSuchElementException("Account(s) not found");
        }

        if (sender.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        FileManager.saveAllUsers("AccountsDB.txt", users);
        FileManager.eTransactionBU(sender, receiver, amount);
    }

    /* ================= CREATE TRANSACTION ================= */

    public Transaction createTransaction(
            String accountNumber,
            AccountTP accountType,
            TransactionType type,
            double amount
    ) {
        List<Transaction> transactions = FileManager.loadTransactions("Transactions.json");

        double balance = 0;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            if (transactions.get(i).getAccountNumber().equals(accountNumber)) {
                balance = transactions.get(i).getBalance();
                break;
            }
        }

        switch (type) {
            case DEPOSIT -> balance += amount;
            case WITHDRAW, ETRANSFER -> balance -= amount;
        }

        Transaction tx = new Transaction(
                accountNumber,
                accountType,
                type,
                balance,
                amount,
                LocalDate.now()
        );

        transactions.add(tx);
        FileManager.saveTransferData("Transactions.json", transactions);

        return tx;
    }

    /* ================= FILTER BY TYPE ================= */

    public List<Transaction> getTransactionsByType(String accountNumber, TransactionType type) {
        return FileManager.loadTransactions("Transactions.json")
                .stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .filter(t -> t.getType() == type)
                .toList();
    }
}
