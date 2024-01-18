package com.example.userservice.repository;

import com.example.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String user);
}
