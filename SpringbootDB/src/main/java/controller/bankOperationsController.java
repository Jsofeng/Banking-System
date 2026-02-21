package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.model.User;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.TransactionType;
import com.example.bankingsystemsb.repository.TransactionJpaRepository;
import com.example.bankingsystemsb.repository.UserJpaRepository;
import com.example.bankingsystemsb.service.TransactionService;
import com.example.bankingsystemsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class bankingOperationsController {

    @Autowired
    private UserService userService;

    private UserJpaRepository userJpaRepository;
    private TransactionJpaRepository transactionJpaRepository;
    private TransactionService transactionService;

    public bankingOperationsController(UserJpaRepository userJpaRepository,  TransactionJpaRepository transactionJpaRepository, TransactionService transactionService) {
        this.userJpaRepository = userJpaRepository;
        this.transactionJpaRepository = transactionJpaRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/secure-test")
    public String secureTest() {
        return "You are authenticated!";
    }

    @GetMapping("/me") // step #1 /auth/login then get a token --> Authorization: Bearer <token> --> /operations/me
    public String getCurrentUser() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/balance")
    public Double getBalance() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userJpaRepository
                .findByUsername(username)
                .orElseThrow();

        return user.getBankingUser().getBalance();
    }


    @PutMapping("/dep") // PROBLEM NOT SAVING
    public ResponseEntity<?> deposit(@RequestParam Long id, @RequestParam Double amount) {
        userService.deposit(id, amount);
        return ResponseEntity.ok("Deposited Successfully");

        /*

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userJpaRepository
                .findByUsername(username)
                .orElseThrow();

        return ResponseEntity.ok(
                transactionService.createTransaction(
                        user.getBankingUser().getDebitCardNumber(),
                        user.getBankingUser().getAccountType(),
                        TransactionType.DEPOSIT,
                        amount
                )
        );
        */
    }


}



