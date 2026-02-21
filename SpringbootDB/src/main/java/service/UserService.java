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


}

