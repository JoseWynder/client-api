package com.josewynder.neoapp.clientapi.security.repository;

import com.josewynder.neoapp.clientapi.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
