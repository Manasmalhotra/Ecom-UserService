package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.models.UserEntity;
import com.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping
    public ResponseEntity<List<UserDTO> > getAllUsers(){
        List<UserDTO>users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id){
        UserDTO user=userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/viewprofile/{id}")
    public ResponseEntity<UserEntity> getfullUser(@PathVariable("id")int id){
        UserEntity user=userService.getfullUser(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserEntity user){
        String response=userService.createUser(user);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> editUser(@PathVariable("id")int id, @RequestBody UserEntity user){
        UserEntity updatedUser=userService.editUser(id,user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        String response=userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
}
