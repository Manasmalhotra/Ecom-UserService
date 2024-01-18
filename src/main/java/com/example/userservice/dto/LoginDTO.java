package com.example.userservice.dto;

import lombok.Getter;

@Getter
public class LoginDTO {
    String mobileNumberOrEmail;
    String password;
}
