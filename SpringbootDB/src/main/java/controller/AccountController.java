package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.Transaction;
import com.example.bankingsystemsb.TransactionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import FileManager.FileManager;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/accounts")

public class AccountController {
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = FileManager.loadTransactions("Transactions.json");

        List<Transaction> userTransactions = transactions.stream().
                                             filter(t -> t.getAccountNumber().equals(accountNumber)).
                                             collect(Collectors.toList());

        return ResponseEntity.ok(userTransactions);
    }

    @GetMapping("/{accountNumber}/transactions/{type}")
    public ResponseEntity<List<Transaction>> getAccountTransactionsByType(@PathVariable String accountNumber, @PathVariable String type) {
        List<Transaction> transactions = FileManager.loadTransactions("Transactions.json");

        TransactionType transactionType;

        try {
            transactionType = TransactionType.valueOf(type.toUpperCase());
        } catch  (Exception e) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<Transaction> filteredByType = transactions.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .filter(t -> t.getType().equals(transactionType))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredByType);
    }

}
