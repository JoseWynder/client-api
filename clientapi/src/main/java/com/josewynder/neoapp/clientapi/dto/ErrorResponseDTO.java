package com.josewynder.neoapp.clientapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Error response for API failures")
public record ErrorResponseDTO(

        @Schema(description = "Time of the error", example = "2025-09-02T20:30:00")
        LocalDateTime timestamp,

        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Error type", example = "Validation Error")
        String error,

        @Schema(description = "Detailed error message", example = "One or more fields are invalid")
        String message,

        @Schema(description = "List of field validation errors")
        List<FieldErrorDTO> fieldErrors
) {

    public static ErrorResponseDTO of(HttpStatus status, String error, String message) {
        return new ErrorResponseDTO(LocalDateTime.now(), status.value(), error, message, null);
    }

    public static ErrorResponseDTO fromValidationErrors(List<FieldErrorDTO> fieldErrors) {
        return new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "One or more fields are invalid",
                fieldErrors
        );
    }
}
