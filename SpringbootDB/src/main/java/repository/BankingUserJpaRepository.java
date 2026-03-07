package com.example.bankingsystemsb.repository;

import com.example.bankingsystemsb.model.BankingUser;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankingUserJpaRepository
        extends JpaRepository<BankingUser, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM BankingUser u WHERE u.id = :id")
    Optional<BankingUser> findByIdForUpdate(@Param("id") Long id);
}
