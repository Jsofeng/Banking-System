package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.model.User;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.TransactionType;
import com.example.bankingsystemsb.repository.TransactionJpaRepository;
import com.example.bankingsystemsb.repository.UserJpaRepository;
import com.example.bankingsystemsb.service.TransactionService;
import com.example.bankingsystemsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class bankingOperationsController {

    @Autowired
    private UserService userService;

    private UserJpaRepository userJpaRepository;
    private TransactionService transactionService;

    public bankingOperationsController(UserJpaRepository userJpaRepository, TransactionService transactionService) {
        this.userJpaRepository = userJpaRepository;
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


    @PutMapping("/dep")
    public ResponseEntity<?> deposit(@RequestParam Double amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deposit(username, amount);
        return ResponseEntity.ok("Deposited Successfully");

    }

    // /endpoint?key=value for @RequestParam
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam String userName, @RequestParam Double amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.withdraw(username, amount);
        return ResponseEntity.ok("Withdraw Successfully");
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam Long senderId,
                                      @RequestParam Long receiverId,
                                      @RequestParam Double amount) {
        transactionService.transfer(senderId, receiverId, amount);
        return ResponseEntity.ok("Transfer Successfully");
    }

    // /endpoint/@pathVariable
    @GetMapping("/transactions/{type}")
    public List<Transaction> getTransactions(@PathVariable TransactionType type) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserTransactionAndType(username, type);
    }

    @GetMapping("/last10/{userId}")
    public List<Transaction> getLast10Transactions(@PathVariable Long userId) {
        return userService.getTop10UserTransactions(userId);

    }

    @GetMapping("/users/{userId}/transactions")
    public Page<Transaction> getUserTransactions(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) TransactionType type) {
        if (type != null) {
            return transactionService.getTransactionsByUserIdAndType(userId, type, page, size);
        } else {
            return transactionService.getTransactionsByUser(userId, page, size);
        }


    }
}



