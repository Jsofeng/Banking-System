package com.example.bankingsystemsb.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions") // table name in PostgreSQL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountTP accountType; // make sure this is an enum

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // enum for DEPOSIT, WITHDRAW, etc.

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate date;
}
