package com.example.userservice.repository;

import com.example.userservice.models.EmailVerificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailVerificationModel,Integer> {
    EmailVerificationModel findByUserId(int userId);
}
