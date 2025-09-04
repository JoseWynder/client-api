package com.josewynder.neoapp.clientapi.security.mapper;

import com.josewynder.neoapp.clientapi.security.dto.UserRequestDTO;
import com.josewynder.neoapp.clientapi.security.dto.UserResponseDTO;
import com.josewynder.neoapp.clientapi.security.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDTO dto) {
        return UserEntity.builder()
                .username(dto.username())
                .password(dto.password())
                .isAdmin(false)
                .build();
    }

    public UserResponseDTO toDTO(UserEntity user) {
        return new UserResponseDTO(user.getUsername(), user.isAdmin());
    }
}
