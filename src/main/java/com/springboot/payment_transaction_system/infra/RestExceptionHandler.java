package com.springboot.payment_transaction_system.infra;

import com.springboot.payment_transaction_system.exceptions.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    //For User class
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDocumentExistsException.class)
    public ResponseEntity<String> handleUserDocumentExistsException(UserDocumentExistsException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<String> handleUserEmailExistsException(UserEmailExistsException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserTypeNotAllowedToMakeTransferException.class)
    public ResponseEntity<String> handleUserTypeNotAllowedToMakeTransferException(UserTypeNotAllowedToMakeTransferException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBalanceInsufficientException.class)
    public ResponseEntity<String> handleUserBalanceInsufficientException(UserBalanceInsufficientException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    //-----

    //For Transaction class

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<String> handleUnauthorizedTransactionException(UnauthorizedTransactionException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    //-----

}
