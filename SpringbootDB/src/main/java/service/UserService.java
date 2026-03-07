package com.example.bankingsystemsb.service;

import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.model.BankingUser;
import com.example.bankingsystemsb.model.User;
import com.example.bankingsystemsb.repository.BankingUserJpaRepository;
import com.example.bankingsystemsb.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private BankingUserJpaRepository bankingUserJpaRepository;

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password, String email, AccountTP accountTP) {
        if(userJpaRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        BankingUser bankingUser = BankingUser.builder()
                .name(username)
                .debitCardNumber("DC-" + System.currentTimeMillis())
                .balance(0.0)
                .active(true)
                .accountType(accountTP)
                .build();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setAccountTP(accountTP);
        user.setActive(true);

        //links both sides
        user.setBankingUser(bankingUser);
        bankingUser.setUser(user);

        return userJpaRepository.save(user);
    }

    @Transactional
    public void deposit(Long id, double depositAmount) {
        BankingUser bankingUser = bankingUserJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        bankingUser.setBalance(bankingUser.getBalance() + depositAmount);
        bankingUserJpaRepository.save(bankingUser);
    }

    @Transactional
    public void withdraw(String userName, double withdrawAmount) {
        if(withdrawAmount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        User user = userJpaRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));

        BankingUser bankingUser = user.getBankingUser();

        if(bankingUser.getBalance() < withdrawAmount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        bankingUser.setBalance(bankingUser.getBalance() - withdrawAmount);
        bankingUserJpaRepository.save(bankingUser);

        transactionService.createTransaction(

                bankingUser.getDebitCardNumber(),
                bankingUser.getAccountType(),
                TransactionType.WITHDRAW,
                withdrawAmount
        );



        }

        public List<Transaction> getUserTransactionAndType(String username, TransactionType type) {
        if (type == null) {
            User user = userJpaRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            return transactionJpaRepository.findAllTransactionsForUser(user.getBankingUser().getId());
        }

        User user =  userJpaRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return transactionJpaRepository.findTransactionsForUserByType(user.getBankingUser().getId(), type);
    }

        public List<Transaction> getTop10UserTransactions(Long userId) {
            return transactionJpaRepository.findTop10ByBankingUserIdOrderByDateDesc(userId);
        }

}

