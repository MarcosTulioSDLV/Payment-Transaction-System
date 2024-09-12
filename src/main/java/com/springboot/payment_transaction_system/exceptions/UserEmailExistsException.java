package com.springboot.payment_transaction_system.exceptions;

public class UserEmailExistsException extends RuntimeException{

    public UserEmailExistsException(){

    }

    public UserEmailExistsException(String message){
        super(message);
    }

}
