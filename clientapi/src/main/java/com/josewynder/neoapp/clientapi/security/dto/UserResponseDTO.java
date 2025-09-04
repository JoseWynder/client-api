package com.josewynder.neoapp.clientapi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data returned after creating a user")
public record UserResponseDTO(
        @Schema(description = "Username of the created user", example = "neoapp")
        String username,

        @Schema(description = "If the user is an administrator", example = "false")
        boolean isAdmin
) {}

