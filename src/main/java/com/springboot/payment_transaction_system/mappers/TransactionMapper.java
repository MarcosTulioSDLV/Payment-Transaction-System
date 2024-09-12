package com.springboot.payment_transaction_system.mappers;

import com.springboot.payment_transaction_system.dtos.TransactionResponseDto;
import com.springboot.payment_transaction_system.models.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionResponseDto toTransactionResponseDto(Transaction transaction){
        return modelMapper.map(transaction,TransactionResponseDto.class);
    }

    public Transaction toTransaction(TransactionResponseDto transactionResponseDto){
        return modelMapper.map(transactionResponseDto,Transaction.class);
    }

}
