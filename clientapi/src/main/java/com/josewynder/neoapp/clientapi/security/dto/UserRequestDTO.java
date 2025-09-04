package com.josewynder.neoapp.clientapi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data to create a new user")
public record UserRequestDTO(

        @Schema(description = "Username of the user", example = "neoapp")
        @NotBlank(message = "Username cannot be empty")
        String username,

        @Schema(description = "Password of the user", example = "123456")
        @NotBlank(message = "Password cannot be empty")
        String password

) {}

