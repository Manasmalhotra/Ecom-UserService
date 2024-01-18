package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.UserEntity;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUser(int id) ;
    UserEntity getfullUser(int id);
    String createUser(UserEntity user);
    UserEntity editUser(int id,UserEntity user);
    String deleteUser(int id);

}
