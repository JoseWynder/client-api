package com.josewynder.neoapp.clientapi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "User login request")
public class LoginRequestDTO {

    @Schema(description = "Username of the user", example = "neoapp")
    private String username;

    @Schema(description = "Password of the user", example = "123456")
    private String password;
}
