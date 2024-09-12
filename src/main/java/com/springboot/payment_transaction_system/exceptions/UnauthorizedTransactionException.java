package com.springboot.payment_transaction_system.exceptions;

public class UnauthorizedTransactionException extends RuntimeException{

    public UnauthorizedTransactionException(){
    }

    public UnauthorizedTransactionException(String message){
        super(message);
    }

}
