package com.example.bankingsystemsb.repository;
import com.example.bankingsystemsb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
}
