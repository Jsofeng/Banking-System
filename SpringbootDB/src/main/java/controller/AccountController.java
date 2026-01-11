package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.model.BankingUser;
import com.example.bankingsystemsb.service.AccountUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountUserService accountService;

    public AccountController(AccountUserService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<BankingUser> createUser(@RequestBody BankingUser user) {
        return ResponseEntity.ok(accountService.createUser(user));
    }

    @PutMapping("/deposit")
    public ResponseEntity<Void> deposit(
            @RequestParam String accountNumber,
            @RequestParam double amount) {

        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<Void> updateUser(
            @PathVariable String accountNumber,
            @RequestBody BankingUser user) {

        accountService.updateUser(accountNumber, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deactivate")
    public ResponseEntity<String> deactivateUser(@RequestParam String accountNumber) {
        accountService.deactivateUser(accountNumber);
        return ResponseEntity.ok("Sucessfully deactivated account");
    }

    @PutMapping("/reactivate")
    public ResponseEntity<Void> reactivateUser(@RequestParam String accountNumber) {
        accountService.reactivateUser(accountNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<BankingUser>> getUsersByStatus(
            @RequestParam boolean active) {

        return ResponseEntity.ok(accountService.getUsersByStatus(active));
    }

    @GetMapping("/paginatedData")
    public ResponseEntity<List<BankingUser>> getPaginatedUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Double minBalance) {

        return ResponseEntity.ok(
                accountService.getUsersPaginated(page, size, sort, order, minBalance)
        );
    }

    @GetMapping("/bankTotal")
    public ResponseEntity<Double> bankTotal() {
        return ResponseEntity.ok(
                accountService.calculateBankTotal("AccountsDB.txt")
        );
    }
}
