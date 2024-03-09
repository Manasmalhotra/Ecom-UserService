package com.example.userservice.controller;


import com.example.userservice.models.EmailVerificationModel;
import com.example.userservice.models.PhoneVerificationModel;
import com.example.userservice.service.ContactVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "{userId}/verify")
public class ContactVerification {
    ContactVerificationService contactVerificationService;
    public ContactVerification(ContactVerificationService contactVerificationService){
        this.contactVerificationService=contactVerificationService;
    }
    @GetMapping("/mobileNumber")
    public ResponseEntity<String> generateOTPForMobile(@RequestBody String phoneNumber) {
        return new ResponseEntity<>(contactVerificationService.sendOtpToMobileNumber(phoneNumber),HttpStatus.OK);
    }
    @GetMapping("/verifyMobileOTP")
    public ResponseEntity<String> verifyUserOTPForMobile(@PathVariable int userId, @RequestBody PhoneVerificationModel verifyObject) throws Exception {
           return ResponseEntity.ok(contactVerificationService.verifyMobileNumber(verifyObject,userId));
    }
    @GetMapping("/email")
    public ResponseEntity<String> generateOTPForEmail(@PathVariable int userId,@RequestBody String email) {
        return new ResponseEntity<>(contactVerificationService.sendOtpToEmail(userId,email),HttpStatus.OK);
    }
    @GetMapping("/verifyEmailOTP")
    public ResponseEntity<String> verifyUserOTPForEmail(@PathVariable int userId, @RequestBody EmailVerificationModel verifyObject) throws Exception {
        return ResponseEntity.ok(contactVerificationService.verifyEmail(verifyObject,userId));
    }
}
