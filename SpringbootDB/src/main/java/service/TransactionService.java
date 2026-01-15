package com.example.bankingsystemsb.service;

import com.example.bankingsystemsb.*;
import FileManager.FileManager;
import org.springframework.stereotype.Service;
import com.example.bankingsystemsb.model.BankingUser;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.model.TransactionType;
import com.example.bankingsystemsb.repository.*;

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
            String accountType,
            String type,
            double amount
    ) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        AccountTP accountTP;
        TransactionType transactionType;

        try {
            accountTP = AccountTP.valueOf(accountType.toUpperCase());
            transactionType = TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid accountType or transaction type");
        }

        List<Transaction> transactions =
                FileManager.loadTransactions("Transactions.json");

        double balance = 0.0;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);

            if (t.getAccountNumber().equals(accountNumber)
                    && t.getAccountType() == accountTP) {
                balance = t.getBalance();
                break;
            }
        }

        switch (transactionType) {
            case DEPOSIT -> balance += amount;
            case WITHDRAW, ETRANSFER -> {
                if (balance < amount) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                balance -= amount;
            }
        }

        Transaction tx = new Transaction(
                accountNumber,
                accountTP,
                transactionType,
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
        if(type == null) {
            return FileManager.loadTransactions("Transactions.json").stream().
                    filter(t -> t.getAccountNumber().equals(accountNumber)).toList();
        }

        return FileManager.loadTransactions("Transactions.json")
                .stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .filter(t -> t.getType() == type)
                .toList();
    }


}
