package com.example.bankingsystemsb.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

    @Column(unique = true, nullable = false)
    private String reference

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
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private BankingUser bankingUser;
}
