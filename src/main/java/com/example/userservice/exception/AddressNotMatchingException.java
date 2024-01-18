package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class AddressNotMatchingException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public AddressNotMatchingException(HttpStatus status,String message){
        super(message);
        this.message=message;
        this.status=status;
    }
    public HttpStatus getStatus(){
        return status;
    }
}
