package com.josewynder.neoapp.clientapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Data to create or update a client")
public record ClientRequestDTO (

    @Schema(description = "Full name of the client", example = "Jose Wynder")
    @NotBlank(message = "Name cannot be empty")
    String name,

    @Schema(description = "CPF number (11 digits)", example = "12345678901")
    @NotBlank(message = "CPF cannot be empty")
    @Size(min = 11, max = 11, message = "CPF must have exactly 11 characters")
    String cpf,

    @Schema(description = "Birth date of the client", example = "2000-01-01")
    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,

    @Schema(description = "Email of the client", example = "josewynder@neoapp.com")
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    String email,

    @Schema(description = "Phone number of the client", example = "+55 (43) 99999-9999")
    String phone

) {}
