package com.example.bankingsystemsb.repository;
import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByAccountNumber(String accountNumber); 


    List<Transaction> findByAccountNumberAndType(String accountNumber, TransactionType type);


    Optional<Transaction> findTopByAccountNumberAndAccountTypeOrderByDateDesc(String accountNumber, AccountTP accountType);

    Transaction findTopByAccountNumberOrderByDateDesc(String fromAccount);
}



