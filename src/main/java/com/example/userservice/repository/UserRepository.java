package com.example.userservice.repository;

import com.example.userservice.models.UserEntity;
import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity>findByEmailOrMobileNo(String email,String mobileNo);
}
