package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    @ManyToOne
    UserEntity user;
    String token;
    LocalDateTime createdAt;
    SessionStatus sessionStatus;

}
