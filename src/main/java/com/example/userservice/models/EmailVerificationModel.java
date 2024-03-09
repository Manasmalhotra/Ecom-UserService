package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmailVerificationModel {
    @Id
    int userId;
    String email;
    int otp;

    public EmailVerificationModel(int  userId,String email,int otp) {
        this.userId=userId;
        this.email=email;
        this.otp=otp;
    }

    public EmailVerificationModel() {

    }
}
