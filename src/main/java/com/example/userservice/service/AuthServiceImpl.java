package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.InvalidCredentialsException;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.*;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.SessionRepository;
import com.example.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{
    UserRepository userRepository;
    ModelMapper modelMapper;
    RoleRepository roleRepository;
    SessionRepository sessionRepository;
    PasswordEncoder passwordEncoder;

    @Value(value="${app.expirationTime}")
    int expirationTime;
    @Value(value="${app.jwt-secret}")
    String jwtSecret;

    public AuthServiceImpl(UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder,SessionRepository sessionRepository, ModelMapper modelMapper){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.modelMapper=modelMapper;
        this.sessionRepository=sessionRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public UserDTO register(UserEntity user) {
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Role role=roleRepository.findByName("USER");
        List<Address> address=user.getAddress();
        if(address!=null) {
            address.get(0).setUser(user);
            user.setAddress(address);
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(role);
        UserEntity savedUser=userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public Session login(String mobileNumberOrEmail, String password) {
        Optional<UserEntity> u=userRepository.findByEmailOrMobileNo(mobileNumberOrEmail,mobileNumberOrEmail);
        if(u.isPresent()){
            UserEntity user=u.get();
            if(!passwordEncoder.matches(password, user.getPassword())){
                System.out.println("HELLO");
                throw new InvalidCredentialsException();
            }
            Optional<Session>s=sessionRepository.findByUserIdAndSessionStatus(user.getId(), SessionStatus.ACTIVE);
            if(s.isPresent()){
                Session session=s.get();
                session.setSessionStatus(SessionStatus.ENDED);
                session.setToken("");
                sessionRepository.save(session);
            }
            Session session=Session.builder()
                    .sessionStatus(SessionStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .token(generateToken(user))
                    .build();
            sessionRepository.save(session);
            return session;
        }
        else {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public Session logout(int id) {
        Session session=sessionRepository.findByUserIdAndSessionStatus(id,SessionStatus.ACTIVE)
                .orElseThrow(()->new ResourceNotFoundException("User","ID",Integer.toString(id)));
        session.setToken("");
        session.setSessionStatus(SessionStatus.ENDED);
        return sessionRepository.save(session);
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    private String generateToken(UserEntity user){
        Date currentDate=new Date();
        Date expiryDate=new Date(currentDate.getTime()+expirationTime);
        Map<String, Object> jsonForJWT = new HashMap<>();
        jsonForJWT.put("userId",user.getId());
        jsonForJWT.put("email", user.getEmail());
        jsonForJWT.put("mobileNumber",user.getMobileNo());
        jsonForJWT.put("role", user.getRole());
        jsonForJWT.put("createdAt", currentDate);
        jsonForJWT.put("expiryAt",expiryDate);
        return Jwts.builder().addClaims(jsonForJWT).signWith(key()).compact();
    }

    private boolean validateToken(String token,String email){
        Claims claims=Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        Date expiryDate= (Date) claims.get("expiryAt");
        String mail=(String) claims.get("email");
        return !(expiryDate.before(new Date())) && email.equals(mail);
    }
}
