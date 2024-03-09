package com.example.userservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailVerifiedEvent {
    String to;
    String from;
    String subject;
    String body;
}
