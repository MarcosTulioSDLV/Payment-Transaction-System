package com.springboot.payment_transaction_system.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.payment_transaction_system.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@AllArgsConstructor
@Getter @Setter @ToString(exclude = {"transactionsAsPayer","transactionsAsPayee"})
@EqualsAndHashCode(exclude = {"transactionsAsPayer","transactionsAsPayee"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String document;//document or CPF

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @JsonIgnore
    @OneToMany(mappedBy = "payer",cascade = CascadeType.REMOVE)
    private List<Transaction> transactionsAsPayer= new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "payee",cascade = CascadeType.REMOVE)
    private List<Transaction> transactionsAsPayee= new ArrayList<>();

    public User(){
    }


}
