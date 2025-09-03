package com.josewynder.neoapp.clientapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

@Schema(description = "Details of a single field validation error")
public record FieldErrorDTO(

        @Schema(description = "Field name", example = "email")
        String field,

        @Schema(description = "Rejected value", example = "invalid-email")
        Object rejectedValue,

        @Schema(description = "Error message", example = "Email must be valid")
        String message
) {
  public static FieldErrorDTO from(FieldError error) {
    return new FieldErrorDTO(
            error.getField(),
            error.getRejectedValue(),
            error.getDefaultMessage()
    );
  }
}
