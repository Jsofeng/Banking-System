package com.example.bankingsystemsb.dto;

import com.example.bankingsystemsb.model.AccountTP;

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private AccountTP accountTP;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountTP(AccountTP accountTP) {
        this.accountTP = accountTP;
    }
    public AccountTP getAccountTP() {
        return accountTP;
    }
}

