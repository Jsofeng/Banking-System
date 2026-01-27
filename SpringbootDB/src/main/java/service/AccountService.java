package com.example.bankingsystemsb.service;
import com.example.bankingsystemsb.model.*;
import com.example.bankingsystemsb.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account updateAccountStatus(String accountNumber, String status) {
       Account account = accountRepository.findByAccountNumber(accountNumber);

       if(account == null) {
        throw new IllegalArgumentException("Account not found");
       }

       AccountStatus accountStatus;

        try {
            accountStatus = AccountStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account status: " + status);
        }

        account.setStatus(accountStatus);

        return accountRepository.save(account);
        }

}

