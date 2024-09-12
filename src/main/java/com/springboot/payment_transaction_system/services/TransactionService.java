package com.springboot.payment_transaction_system.services;

import com.springboot.payment_transaction_system.dtos.TransactionRequestDto;
import com.springboot.payment_transaction_system.dtos.TransactionResponseDto;
import com.springboot.payment_transaction_system.models.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    Page<TransactionResponseDto> getTransactions(Pageable pageable);

    TransactionResponseDto getTransactionById(Long id);

    @Transactional
    TransactionResponseDto addTransaction(TransactionRequestDto transactionRequestDto);

}
