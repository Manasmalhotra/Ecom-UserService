package com.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> ResourceNotFoundExceptionHandler(ResourceNotFoundException e, WebRequest w){
        ErrorDetails error=new ErrorDetails(new Date(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotMatchingException.class)
    public ResponseEntity<ErrorDetails> AddressNotMatchingExceptionHandler(AddressNotMatchingException e,WebRequest w){
        ErrorDetails error=new ErrorDetails(new Date(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<>(error,e.getStatus());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDetails> InvalidCredentialsExceptionHandler(InvalidCredentialsException e, WebRequest w){
        ErrorDetails error=new ErrorDetails(new Date(),e.getMessage(),w.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(VerificationFailedException.class)
    public ResponseEntity<String> VerificationFailedExceptionHandler(VerificationFailedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
