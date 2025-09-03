package com.josewynder.neoapp.clientapi.dto;

import com.josewynder.neoapp.clientapi.model.Client;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.Period;

@Schema(description = "Client data returned by the API")
public record ClientResponseDTO (

    @Schema(description = "Unique identifier of the client", example = "1")
    Long id,

    @Schema(description = "Full name of the client", example = "Wynder Jose")
    String name,

    @Schema(description = "CPF number (11 digits)", example = "12345678901")
    String cpf,

    @Schema(description = "Birth date of the client", example = "2000-01-01")
    LocalDate birthDate,

    @Schema(description = "Email of the client", example = "josewynder@neoapp.com")
    String email,

    @Schema(description = "Phone number of the client", example = "+55 (43) 99999-9999")
    String phone,

    @Schema(description = "Age of the client calculated from birth date", example = "25")
    int age

) {
    public static ClientResponseDTO fromEntity(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getBirthDate(),
                client.getEmail(),
                client.getPhone(),
                Period.between(client.getBirthDate(), LocalDate.now()).getYears()
        );
    }
}
