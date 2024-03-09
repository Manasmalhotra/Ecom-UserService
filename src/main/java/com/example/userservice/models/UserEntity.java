package com.example.userservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String firstName;
    String lastName;
    @Email
    @Column(nullable = false,unique=true)
    String email;
    String password;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    LocalDate dateOfBirth;
    @Column(nullable = false,unique=true,length=10)
    String mobileNo;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    List<Address> address;
    @JsonIgnore
    LocalDateTime createdAt;
    @ManyToOne
    Role role;
    int isMobileVerfied;
    int isEmailVerified;
}
