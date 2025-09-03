package com.josewynder.neoapp.clientapi.mapper;

import com.josewynder.neoapp.clientapi.dto.ClientRequestDTO;
import com.josewynder.neoapp.clientapi.dto.ClientResponseDTO;
import com.josewynder.neoapp.clientapi.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        return Client.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .birthDate(dto.birthDate())
                .email(dto.email())
                .phone(dto.phone())
                .build();
    }

    public ClientResponseDTO toDTO(Client client) {
        return ClientResponseDTO.fromEntity(client);
    }
}