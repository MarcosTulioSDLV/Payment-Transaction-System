package com.springboot.payment_transaction_system.services;

import com.springboot.payment_transaction_system.dtos.UserRequestDto;
import com.springboot.payment_transaction_system.dtos.UserResponseDto;
import com.springboot.payment_transaction_system.models.User;
import com.springboot.payment_transaction_system.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface UserService {


    Page<UserResponseDto> getUsers(Pageable pageable);

    UserResponseDto getUserById(Long id);

    User findUserById(Long id);

    @Transactional
    UserResponseDto addUser(UserRequestDto userRequestDto);

    @Transactional
    UserResponseDto removeUser(Long id);

    @Transactional
    UserResponseDto updateUser(UserRequestDto userRequestDto,Long id);

    void validateUserTypeAllowedToMakeTransfer(User user);

    void validateTransactionAmount(User user,BigDecimal amount);

}
