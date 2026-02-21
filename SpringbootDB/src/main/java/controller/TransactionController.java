package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.model.*;
import com.example.bankingsystemsb.repository.AccountRepository;
import com.example.bankingsystemsb.service.AccountService;
import com.example.bankingsystemsb.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService; 
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.accountService = new AccountService(accountRepository);
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
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam double amount) {

        // Fetch accounts from the database
        Account from = accountRepository.findByAccountNumber(fromAccountNumber);
        if(from == null) {
            throw new IllegalArgumentException("From account not found");
        }
        Account to = accountRepository.findByAccountNumber(toAccountNumber);
        if(to == null) {
            throw new IllegalArgumentException("To account not found");
        }

        transactionService.transfer(from, to, amount);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferData")
    public ResponseEntity<Transaction> transferData(
            @RequestParam String accountNumber,
            @RequestParam AccountTP accountType,
            @RequestParam TransactionType type,
            @RequestParam double amount) {

        return ResponseEntity.ok(transactionService.createTransaction(accountNumber, accountType, type, amount));
    }

    @GetMapping("/transaction/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @PathVariable String accountNumber,
            @RequestParam(required = false) TransactionType type) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByType(accountNumber, type)
        );
    }
    //@PostMapping("/transaction/create") for @RequestParam
    @PostMapping("/transaction/create/{accountNumber}/{accountType}/{type}/{amount}")
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable String accountNumber,
            @PathVariable AccountTP accountType,
            @PathVariable TransactionType type,
            @PathVariable double amount) {

        return ResponseEntity.ok(transactionService.createTransaction(accountNumber, accountType, type, amount));
    }

    @PutMapping("/accounts/updateStatus/{accountNumber}")
    public ResponseEntity<Account> updateStatus(@PathVariable String accountNumber, @RequestParam String status) {

        return ResponseEntity.ok(accountService.updateAccountStatus(accountNumber, status));
    }
}
