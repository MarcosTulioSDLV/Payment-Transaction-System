package com.springboot.payment_transaction_system.dtos;

import com.springboot.payment_transaction_system.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class TransactionRequestDto {

    @PositiveOrZero
    @NotNull
    private BigDecimal amount;

    private Long payerId;

    private Long payeeId;

}
