package com.springboot.payment_transaction_system.dtos;

import com.springboot.payment_transaction_system.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class TransactionResponseDto {

    private Long id;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

    private User payer;

    private User payee;

}
