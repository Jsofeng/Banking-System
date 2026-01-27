package com.example.bankingsystemsb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banking_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor   
@Builder
public class BankingUser { 


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String debitCardNumber;

    @Column(nullable = false)
    private double balance;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;  
}
