package com.example.userservice.controller;

import com.example.userservice.dto.LoginDTO;
import com.example.userservice.models.Session;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.UserEntity;
import com.example.userservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO>register(@RequestBody UserEntity user){
        return ResponseEntity.ok(authService.register(user));
    }
    @PostMapping("/signin")
    public ResponseEntity<Session> login(@RequestBody LoginDTO user) {
        return ResponseEntity.ok(authService.login(
                user.getMobileNumberOrEmail(),
                user.getPassword()));
    }
    @PostMapping("/logout/{id}")
    public ResponseEntity<Session> logout(@PathVariable int id){
        return ResponseEntity.ok(authService.logout(id));
    }
}
