package com.springboot.payment_transaction_system.exceptions;

public class UserDocumentExistsException extends RuntimeException{

    public UserDocumentExistsException(){
    }

    public UserDocumentExistsException(String message){
        super(message);
    }

}
