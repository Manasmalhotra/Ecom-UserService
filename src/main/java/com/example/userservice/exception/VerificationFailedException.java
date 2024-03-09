package com.example.userservice.exception;

public class VerificationFailedException extends RuntimeException{

    public VerificationFailedException(String message){
        super(message);
    }
}
