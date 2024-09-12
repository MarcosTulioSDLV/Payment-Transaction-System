package com.springboot.payment_transaction_system.controllers;

import com.springboot.payment_transaction_system.dtos.TransactionRequestDto;
import com.springboot.payment_transaction_system.dtos.TransactionResponseDto;
import com.springboot.payment_transaction_system.models.Transaction;
import com.springboot.payment_transaction_system.services.TransactionService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity<Page<TransactionResponseDto>> getTransactions(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(transactionService.getTransactions(pageable));
    }

    @GetMapping(value = "/transactions-by-id/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity<TransactionResponseDto> addTransaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto){
        return new ResponseEntity<>(transactionService.addTransaction(transactionRequestDto),HttpStatus.CREATED);
    }


}
