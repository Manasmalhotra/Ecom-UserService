package com.example.userservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class phoneVerificationModel {
    String phoneNumber;
    String otp;
}
