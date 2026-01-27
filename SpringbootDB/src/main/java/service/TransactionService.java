package com.example.bankingsystemsb.service;

import com.example.bankingsystemsb.model.*;
import com.example.bankingsystemsb.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionJpaRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionJpaRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /* ================= ACCOUNT SUMMARY ================= */
    public List<Transaction> getAccountSummary(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    /* ================= FILTER BY TYPE ================= */
    public List<Transaction> getTransactionsByType(String accountNumber, TransactionType type) {
        if (type == null) {
            return transactionRepository.findByAccountNumber(accountNumber);
        }
        return transactionRepository.findByAccountNumberAndType(accountNumber, type);
    }

    /* ================= CREATE TRANSACTION ================= */
    public Transaction createTransaction(String accountNumber, String accountType, String type, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        AccountTP accountTP;
        TransactionType transactionType;

        try {
            accountTP = AccountTP.valueOf(accountType.toUpperCase());
            transactionType = TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid accountType or transaction type");
        }

        // Get last balance from DB
        double balance = transactionRepository.findTopByAccountNumberAndAccountTypeOrderByDateDesc(accountNumber, accountTP)
                .map(Transaction::getBalance)
                .orElse(0.0);

        switch (transactionType) {
            case DEPOSIT -> balance += amount;
            case WITHDRAW, ETRANSFER -> {
                if (balance < amount) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                balance -= amount;
            }
        }

        Transaction tx = Transaction.builder()
                .accountNumber(accountNumber)
                .accountType(accountTP)
                .type(transactionType)
                .balance(balance)
                .amount(amount)
                .date(LocalDate.now())
                .build();

        return transactionRepository.save(tx); // persists in DB
    }

    @Transactional
    public List<Transaction> transfer(Account from, Account to, double amount) {
        if(from.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("from is not ACTIVE");
        }

        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // 1️⃣ Update balances
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        // 2️⃣ Sender transaction
        Transaction outTx = Transaction.builder()
                .accountNumber(from.getAccountNumber())
                .accountType(from.getAccountType())
                .type(TransactionType.TRANSFER_OUT)
                .amount(amount)
                .balance(from.getBalance())
                .date(LocalDate.now())
                .build();

        // 3️⃣ Receiver transaction
        Transaction inTx = Transaction.builder()
                .accountNumber(to.getAccountNumber())
                .accountType(to.getAccountType())
                .type(TransactionType.TRANSFER_IN)
                .amount(amount)
                .balance(to.getBalance())
                .date(LocalDate.now())
                .build();

        transactionRepository.save(outTx);
        transactionRepository.save(inTx);

        return List.of(outTx, inTx);
    }


}
