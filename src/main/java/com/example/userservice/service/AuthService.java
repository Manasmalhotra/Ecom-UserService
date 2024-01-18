package com.example.userservice.service;

import com.example.userservice.models.Session;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.UserEntity;


public interface AuthService {

    UserDTO register(UserEntity user);

    Session login(String mobileNumberOrEmail, String password);

    Session logout(int id);
}
