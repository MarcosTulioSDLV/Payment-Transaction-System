package com.springboot.payment_transaction_system.dtos;

import com.springboot.payment_transaction_system.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class UserResponseDto {

    private Long id;

    private String fullName;

    private String document;

    private String email;

    private String password;

    private BigDecimal balance;

    private UserType userType;

}
