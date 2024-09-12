package com.springboot.payment_transaction_system.exceptions;

public class UserBalanceInsufficientException extends RuntimeException{

    public UserBalanceInsufficientException(){
    }

    public UserBalanceInsufficientException(String message){
        super(message);
    }

}
