package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.TransactionType;
import com.example.bankingsystemsb.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/summary/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAccountSummary(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                transactionService.getAccountSummary(accountNumber)
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam double amount) {

        transactionService.transfer(fromAccount, toAccount, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/type/{accountNumber}/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @PathVariable String accountNumber,
            @PathVariable TransactionType type) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByType(accountNumber, type)
        );
    }
}
