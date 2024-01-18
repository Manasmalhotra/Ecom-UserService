package com.example.userservice.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Invalid Credentials. Please try again");
    }
}
