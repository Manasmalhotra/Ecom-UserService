package com.example.userservice.service;


import com.example.userservice.models.EmailVerificationModel;
import com.example.userservice.models.PhoneVerificationModel;
import org.springframework.stereotype.Service;

@Service
public interface ContactVerificationService {
     String sendOtpToMobileNumber(String phoneNumber);
     String sendOtpToEmail(int userId,String email);
     String verifyMobileNumber(PhoneVerificationModel verifyObject, int userId);

     String verifyEmail(EmailVerificationModel verifyObject, int userId);
}
