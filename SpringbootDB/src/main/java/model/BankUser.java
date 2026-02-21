package com.example.bankingsystemsb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banking_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor   // Needed for @Builder
@Builder
public class BankingUser { //JAVA_HOME=$(/usr/libexec/java_home -v 17) mvn clean install (runs on java v 17 only)

    

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
    private boolean active = true;  // default value for builder

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountTP accountType;

    //BankingUser.java is referenced by User.java
    @OneToOne(mappedBy = "bankingUser") 
    private User user;
}
