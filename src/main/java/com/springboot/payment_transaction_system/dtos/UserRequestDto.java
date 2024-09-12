package com.springboot.payment_transaction_system.dtos;

import com.springboot.payment_transaction_system.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class UserRequestDto {

    @NotBlank
    private String fullName;

    @NotBlank
    private String document;

    @Email
    @NotNull
    private String email;

    @NotBlank
    private String password;

    @PositiveOrZero
    @NotNull
    private BigDecimal balance;

    @NotNull
    private UserType userType;

}
