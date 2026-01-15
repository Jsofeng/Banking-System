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

 @PostMapping("/transferData")
    public ResponseEntity<Transaction> transferData(
            @RequestParam String accountNumber,
            @RequestParam String accountType,
            @RequestParam String type,
            @RequestParam double amount) {

        return ResponseEntity.ok(transactionService.createTransaction(accountNumber, accountType, type, amount));
    }

    @GetMapping("/transaction/{accountNumber}") //http://localhost:8080/transactions/transaction/577420589159?&type=DEPOSIT
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @PathVariable String accountNumber,
            @RequestParam(required = false) TransactionType type) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByType(accountNumber, type)
        );
    }

    @GetMapping("/transaction/create/{accountNumber}/{accountType}/{type}/{amount}")
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable String accountNumber,
            @PathVariable String accountType,
            @PathVariable String type,
            @PathVariable double amount) {

        return ResponseEntity.ok(transactionService.createTransaction(accountNumber, accountType, type, amount));
    }

}

