package com.springboot.payment_transaction_system.exceptions;

public class UserTypeNotAllowedToMakeTransferException extends RuntimeException{

    public UserTypeNotAllowedToMakeTransferException(){
    }

    public UserTypeNotAllowedToMakeTransferException(String message){
        super(message);
    }

}
