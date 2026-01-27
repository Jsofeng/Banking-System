package com.example.bankingsystemsb.service;

import com.example.bankingsystemsb.model.*;
import com.example.bankingsystemsb.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service // handles logic only
public class AccountUserService {

    private final BankingUserJpaRepository repository;

    public AccountUserService(BankingUserJpaRepository repository) {
        this.repository = repository;
    }

    public BankingUser createUser(BankingUser user) {
        if (user.getDebitCardNumber() == null ||
                !user.getDebitCardNumber().matches("\\d{10,12}")) {
            throw new IllegalArgumentException("Invalid debit card number");
        }
        return repository.save(user);
    }

    public List<BankingUser> getAllUsers() {
        return repository.findAll();
    }

    public BankingUser getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public void deposit(Long userId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        BankingUser user = getUser(userId);
        user.setBalance(user.getBalance() + amount);
        repository.save(user);
    }


    public List<BankingUser> getActiveUsers() {
        return repository.findAll()
                .stream()
                .filter(BankingUser::isActive)
                .toList();
    }

    public BankingUser updateUser(Long id,  BankingUser user) {
        BankingUser existingUser = repository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        if(!existingUser.isActive()) {
            throw new IllegalArgumentException("User is not active");
        }

        existingUser.setName(user.getName());
        existingUser.setDebitCardNumber(user.getDebitCardNumber());
        existingUser.setBalance(user.getBalance());

        return repository.save(existingUser);



    }

    public void deactivateAccount(Long id) {
        if(!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }

        BankingUser user = getUser(id);
        user.setActive(false);
        repository.save(user);
    }

    public void reactivateAccount(Long id) {
        if(!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }

        BankingUser user = getUser(id);
        user.setActive(true);
        repository.save(user);
    }

}
