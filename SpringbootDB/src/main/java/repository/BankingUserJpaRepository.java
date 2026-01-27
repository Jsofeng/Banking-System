package com.example.bankingsystemsb.repository;

import com.example.bankingsystemsb.model.BankingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingUserJpaRepository
        extends JpaRepository<BankingUser, Long> {
}
