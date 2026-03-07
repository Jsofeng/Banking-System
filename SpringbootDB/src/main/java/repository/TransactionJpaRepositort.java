package com.example.bankingsystemsb.repository;
import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.model.Transaction;
import com.example.bankingsystemsb.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByAccountNumber(String accountNumber); 


    List<Transaction> findByAccountNumberAndType(String accountNumber, TransactionType type);


    Optional<Transaction> findTopByAccountNumberAndAccountTypeOrderByDateDesc(String accountNumber, AccountTP accountType);

    Transaction findTopByAccountNumberOrderByDateDesc(String fromAccount);

     @Query("SELECT t FROM Transaction t JOIN t.bankingUser bu WHERE bu.id = :id")
    List<Transaction> findAllTransactionsForUser(@Param("id") Long bankingUserId);

    @Query("SELECT t FROM Transaction t WHERE t.bankingUser.id = :id AND t.type = :type")
    List<Transaction> findTransactionsForUserByType(
            @Param("id") Long bankingUserId,
            @Param("type") TransactionType type);

    List<Transaction> findTop10ByBankingUserIdOrderByDateDesc(Long id);

    Page<Transaction> findByBankingUserId(Long bankingUserId, Pageable pageable);

    Page<Transaction> findByBankingUserIdAndType(Long id, TransactionType type, Pageable pageable);

}



