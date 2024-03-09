package com.example.userservice.repository;

import com.example.userservice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity>findByEmailOrMobileNo(String email,String mobileNo);
    @Modifying
    @Query("update UserEntity u set u.isMobileVerfied = 1 where u.id = :userId")
    void updateMobileVerificationStatus(@Param(value = "id") int userId);

    @Modifying
    @Query("update UserEntity u set u.isEmailVerified = 1 where u.id = :userId")
    void updateEmailVerificationStatus(@Param(value = "id") int userId);
}
