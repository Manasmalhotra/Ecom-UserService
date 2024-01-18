package com.example.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    Date timestamp;
    String message;
    String details;

}
