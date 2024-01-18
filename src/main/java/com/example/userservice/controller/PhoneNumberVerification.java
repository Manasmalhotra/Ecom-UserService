package com.example.userservice.controller;

import com.example.userservice.models.phoneVerificationModel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/{userId}/verify/phoneNumber")
public class PhoneNumberVerification {
    @Value(value="${app.TWILIO_ACCOUNT_SID}")
    String accountSid;
    @Value(value="${app.TWILIO_AUTH_TOKEN}")
    String authToken;
    @GetMapping(value = "/generateOTP")
    public ResponseEntity<String> generateOTP(@PathVariable int userId, @RequestBody String phoneNumber) {

        Twilio.init(accountSid,authToken);

        Verification verification = Verification.creator(
                        "VA3d401c55ae5fc3435800487ab7177fba", // this is your verification sid
                        phoneNumber, //this is your Twilio verified recipient phone number
                        "sms") // this is your channel type
                .create();
        Message message = Message
                .creator(new PhoneNumber(phoneNumber),new PhoneNumber("+918368455166"),"Hello")
                .create();
        System.out.println(verification.getStatus());
        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }
    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP(@RequestBody phoneVerificationModel verifyObject) throws Exception {
        Twilio.init(accountSid,authToken);

        try {

            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VA3d401c55ae5fc3435800487ab7177fba")
                    .setTo(verifyObject.getPhoneNumber())
                    .setCode(verifyObject.getOtp())
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
    }
}
