package com.example.bankingsystemsb.controller;

import com.example.bankingsystemsb.dto.RegisterRequest;
import com.example.bankingsystemsb.model.AccountTP;
import com.example.bankingsystemsb.service.UserService;
import com.example.bankingsystemsb.model.LoginRequest;
import com.example.bankingsystemsb.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                                          (request.getUsername(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Set a default account type if none is provided
        AccountTP accountType = request.getAccountTP() != null ? request.getAccountTP() : AccountTP.CHEQUING;
        userService.register(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                accountType
        );

        return ResponseEntity.ok("User registered successfully");
    }




}

