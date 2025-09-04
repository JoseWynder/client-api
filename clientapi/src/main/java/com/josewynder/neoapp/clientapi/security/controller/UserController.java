package com.josewynder.neoapp.clientapi.security.controller;

import com.josewynder.neoapp.clientapi.security.dto.LoginRequestDTO;
import com.josewynder.neoapp.clientapi.security.dto.LoginResponseDTO;
import com.josewynder.neoapp.clientapi.security.dto.UserRequestDTO;
import com.josewynder.neoapp.clientapi.security.dto.UserResponseDTO;
import com.josewynder.neoapp.clientapi.security.mapper.UserMapper;
import com.josewynder.neoapp.clientapi.security.model.UserEntity;
import com.josewynder.neoapp.clientapi.security.jwt.JwtTokenService;
import com.josewynder.neoapp.clientapi.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Manage users and authentication")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequest) {
        UserEntity user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userService.save(user);
        return userMapper.toDTO(savedUser);
    }

    @PostMapping("/auth")
    @Operation(summary = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public LoginResponseDTO authenticate(@RequestBody LoginRequestDTO credentials) {
        UserEntity user = UserEntity.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build();

        UserDetails authenticatedUser = userService.authenticate(user);
        String token = jwtTokenService.generateToken(user);

        return new LoginResponseDTO(user.getUsername(), token);
    }
}
