package com.springboot.payment_transaction_system.exceptions;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(){
    }

    public TransactionNotFoundException(String message){
        super(message);
    }

}
