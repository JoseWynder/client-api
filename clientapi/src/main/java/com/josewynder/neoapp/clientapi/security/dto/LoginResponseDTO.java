package com.josewynder.neoapp.clientapi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response returned after successful authentication")
public class LoginResponseDTO {

    @Schema(description = "Username of the authenticated user", example = "neoapp")
    private String username;

    @Schema(description = "JWT token issued after authentication", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;
}
