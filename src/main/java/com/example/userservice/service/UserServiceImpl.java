package com.example.userservice.service;

import com.example.userservice.configuration.KafkaProducerConfig;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.*;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.repository.EmailRepository;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    ModelMapper modelMapper;
    KafkaProducerConfig kafkaProducerConfig;


    private final RoleRepository roleRepository;
    ObjectMapper objectMapper;
    EmailRepository emailRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           RoleRepository roleRepository, KafkaProducerConfig kafkaProducerConfig,
                           ObjectMapper objectMapper,
                           EmailRepository emailRepository){
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
        this.roleRepository = roleRepository;
        this.kafkaProducerConfig=kafkaProducerConfig;
        this.objectMapper=objectMapper;
        this.emailRepository=emailRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity>users=userRepository.findAll();
        List<UserDTO> allUsers= users.stream().map(user->modelMapper.map(user, UserDTO.class))
                                                                    .collect(Collectors.toList());
        return allUsers;
    }


    public UserDTO getUser(int id){
        UserEntity u=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","ID",Integer.toString(id)));
        return modelMapper.map(u, UserDTO.class);
    }

    public UserEntity getfullUser(int id){
        Optional<UserEntity> user=userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new ResourceNotFoundException("User","ID",Integer.toString(id));
    }


    public String createUser(UserEntity u) {
        UserEntity user=modelMapper.map(u, UserEntity.class);
        Role role=roleRepository.findByName("USER");
        List<Address> address=user.getAddress();
        address.get(0).setUser(user);
        user.setAddress(address);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(role);
        user.setIsMobileVerfied(0);
        user.setIsEmailVerified(0);
        userRepository.save(user);
        return "Profile Created Successfully!";
    }

    public UserEntity editUser(int id,UserEntity user) throws ResourceNotFoundException {
        Optional<UserEntity> u=userRepository.findById(id);
        if(u.isPresent()){
            UserEntity userResult=u.get();
            userResult.setFirstName(user.getFirstName());
            userResult.setLastName(user.getLastName());
            userResult.setEmail(user.getEmail());
            userResult.setPassword(user.getPassword());
            userResult.setAddress(user.getAddress());
            userResult.setMobileNo(user.getMobileNo());
            userResult.setDateOfBirth(user.getDateOfBirth());
            return userRepository.save(userResult);
        }
        throw new ResourceNotFoundException("User","ID",Integer.toString(id));
    }

    public String deleteUser(int id){
        UserEntity user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","ID",Integer.toString(id)));
        userRepository.delete(user);
        return "User Deleted Successfully!";
    }

    @Override
    public UserEntity getUserFromEmailOrMobileNo(String emailOrmobile) {
        return userRepository.findByEmailOrMobileNo(emailOrmobile,emailOrmobile).get();
    }

    @Override
    public void updateMobileVerificationStatus(int userId,String phoneNumber) {
        userRepository.updateMobileVerificationStatus(userId);
        MobileVerifiedEvent mobileVerifiedEvent=new MobileVerifiedEvent("8368455166",
                phoneNumber,
                "Hi, Welcome to MyProduct.com");
        try{
            kafkaProducerConfig.sendMessage("mobileVerificationSuccess",objectMapper.writeValueAsString(mobileVerifiedEvent));
        }
        catch(Exception e){
            System.out.println("Something went wrong!");
        }
    }

    @Override
    public void saveEmailOTP(int userId, String emailAddress, int otp) {
        EmailVerificationModel emailVerification=new EmailVerificationModel(userId,emailAddress,otp);
        emailRepository.save(emailVerification);
    }

    @Override
    public int getEmailOtp(int userId) {
        return emailRepository.findByUserId(userId).getOtp();
    }

    @Override
    public void updateEmailVerificationStatus(int userId, String email) {
        userRepository.updateEmailVerificationStatus(userId);
        EmailVerifiedEvent emailVerifiedEvent=new EmailVerifiedEvent(email,"myproduct@gmail.com","Welcome to MyProduct.com","Thanks for verifying your email!");
        try{
            kafkaProducerConfig.sendMessage("emailVerificationSuccess",objectMapper.writeValueAsString(emailVerifiedEvent));
        }
        catch(Exception e){
            System.out.println("Something went wrong!");
        }
    }
}
