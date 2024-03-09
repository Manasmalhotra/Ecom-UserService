package com.example.userservice.service;


import com.example.userservice.exception.VerificationFailedException;
import com.example.userservice.models.EmailVerificationModel;
import com.example.userservice.models.PhoneVerificationModel;
import com.example.userservice.util.EmailUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class ContactVerificationServiceImpl implements ContactVerificationService {
    @Value(value="${app.TWILIO_ACCOUNT_SID}")
    String accountSid;
    @Value(value="${app.TWILIO_AUTH_TOKEN}")
    String authToken;
    UserService userService;
    public ContactVerificationServiceImpl(UserService userService){
        this.userService=userService;
    }
    @Override
    public String sendOtpToMobileNumber(String phoneNumber) {
        Twilio.init(accountSid,authToken);

        Verification verification = Verification.creator(
                        "VA3d401c55ae5fc3435800487ab7177fba", // this is your verification sid
                        phoneNumber, //this is your Twilio verified recipient phone number
                        "sms") // this is your channel type
                .create();

        Message message = Message
                .creator(new PhoneNumber(phoneNumber),new PhoneNumber("+918368455166"),"Hello ")
                .create();
        System.out.println(verification.getStatus());
        return "Your OTP has been sent to your phone number";
    }

    @Override
    public String sendOtpToEmail(int userId,String toEmail) {
        final String fromEmail = "myproduct@gmail.com"; //requires valid gmail id
        final String password = "mypassword"; // correct password for gmail id

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        EmailUtil.sendEmail(session, toEmail,"Your OTP", "Hi, your OTP for verification: "+otp);
        userService.saveEmailOTP(userId,toEmail,otp);
        return "OTP has been sent to your email for verification";
    }

    @Override
    public String verifyMobileNumber(PhoneVerificationModel verifyObject, int userId) {
        Twilio.init(accountSid,authToken);

        try {

            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VA3d401c55ae5fc3435800487ab7177fba")
                    .setTo(verifyObject.getPhoneNumber())
                    .setCode(verifyObject.getOtp())
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            throw new VerificationFailedException("Mobile Number Verification Failed");
        }
        userService.updateMobileVerificationStatus(userId,verifyObject.getPhoneNumber());
        return "Your mobile verification has been completed successfully";
    }

    @Override
    public String verifyEmail(EmailVerificationModel verifyObject, int userId) {
        int otp=userService.getEmailOtp(verifyObject.getUserId());
        if(otp== verifyObject.getOtp()) {
            userService.updateEmailVerificationStatus(userId,verifyObject.getEmail());
            return "Your Email has been verified successfully";
        }
        throw new VerificationFailedException("Email Verification Failed");
    }
}
