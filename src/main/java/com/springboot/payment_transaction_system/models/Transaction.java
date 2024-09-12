package com.springboot.payment_transaction_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_TRANSACTION")
@AllArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name="payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name="payee_id")
    private User payee;

    public Transaction(){
    }

}
