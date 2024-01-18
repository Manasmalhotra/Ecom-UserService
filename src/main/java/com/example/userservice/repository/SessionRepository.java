package com.example.userservice.repository;

import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Integer> {
    Optional<Session> findByUserIdAndSessionStatus(int id, SessionStatus status);
}
