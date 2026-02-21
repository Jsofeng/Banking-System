package com.example.bankingsystemsb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountTP accountTP;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banking_user_id") //@JoinColumn → this table owns the foreign key
   
    private BankingUser bankingUser;

}
