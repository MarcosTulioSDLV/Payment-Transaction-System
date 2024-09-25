package com.springboot.payment_transaction_system.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.payment_transaction_system.dtos.TransactionRequestDto;
import com.springboot.payment_transaction_system.dtos.TransactionResponseDto;
import com.springboot.payment_transaction_system.enums.UserType;
import com.springboot.payment_transaction_system.exceptions.TransactionNotFoundException;
import com.springboot.payment_transaction_system.exceptions.UnauthorizedTransactionException;
import com.springboot.payment_transaction_system.exceptions.UserBalanceInsufficientException;
import com.springboot.payment_transaction_system.exceptions.UserTypeNotAllowedToMakeTransferException;
import com.springboot.payment_transaction_system.mappers.TransactionMapper;
import com.springboot.payment_transaction_system.models.Transaction;
import com.springboot.payment_transaction_system.models.User;
import com.springboot.payment_transaction_system.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionServiceImp implements TransactionService{

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    private final TransactionMapper transactionMapper;

    private final RestTemplate restTemplate;


    @Autowired
    public TransactionServiceImp(TransactionRepository transactionRepository, UserService userService, TransactionMapper transactionMapper, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.transactionMapper = transactionMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    public Page<TransactionResponseDto> getTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(transactionMapper::toTransactionResponseDto);
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction= findTransactionById(id);
        return transactionMapper.toTransactionResponseDto(transaction);
    }

    private Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(()->new TransactionNotFoundException("Transaction with id: "+ id +" not found!"));
    }


    @Override
    @Transactional
    public TransactionResponseDto addTransaction(TransactionRequestDto transactionRequestDto) {
        User payer= userService.findUserById(transactionRequestDto.getPayerId());
        User payee= userService.findUserById(transactionRequestDto.getPayeeId());
        BigDecimal amount= transactionRequestDto.getAmount();

        userService.validateUserTypeAllowedToMakeTransfer(payer);
        userService.validateTransactionAmount(payer,amount);

        authorizeTransaction();

        payer.setBalance(payer.getBalance().subtract(amount));
        payee.setBalance(payee.getBalance().add(amount));

        Transaction transaction= new Transaction(null,amount,LocalDateTime.now(ZoneId.of("UTC-5")),payer,payee);//UTC-5 For Colombian local time
        return transactionMapper.toTransactionResponseDto(transactionRepository.save(transaction));
    }

    //Authorize transaction with external service
    private void authorizeTransaction(){
        if(!isAuthorizedTransaction())
            throw new UnauthorizedTransactionException("Unauthorized Transaction!");
    }

    private boolean isAuthorizedTransaction(){
        try{
            ResponseEntity<JsonNode> authorizeTransactionResponse= restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize",JsonNode.class);

            // Check if the response status is 200 OK
            if((authorizeTransactionResponse.getStatusCode() != HttpStatus.OK))
                return false;

            JsonNode rootNode= authorizeTransactionResponse.getBody();

            String statusResponse= Objects.requireNonNull(rootNode).get("status").asText();
            if(!statusResponse.equalsIgnoreCase("success"))
                return false;

            JsonNode dataResponse= rootNode.get("data");
            Boolean authorized= dataResponse.get("authorization").asBoolean();
            return authorized;

            //by using Map
            /*
             ResponseEntity<Map> authorizeTransactionResponse= restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize",Map.class);

            // Check if the response status is 200 OK
            if((authorizeTransactionResponse.getStatusCode() != HttpStatus.OK))
                return false;

            String statusResponse= (String) authorizeTransactionResponse.getBody().getOrDefault("status","");
            if(!statusResponse.equalsIgnoreCase("success"))
                return false;

            Map<String,Object> dataResponse= (Map<String,Object>)authorizeTransactionResponse.getBody().get("data");
            Boolean authorized= (Boolean) dataResponse.getOrDefault("authorization",false);
            return authorized;
             */
        }
        catch (HttpClientErrorException e) {
            //Handle specific HTTP error codes
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) { //Handle 403 Forbidden error
                return false; //Or handle differently if necessary
            }
            //Handle other HTTP error codes if needed
            return false;
        } catch (RestClientException e) {
            //Handle any other REST client exceptions
            return false;
        }
    }


}
