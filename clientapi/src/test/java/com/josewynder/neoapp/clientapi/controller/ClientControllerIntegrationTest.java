package com.josewynder.neoapp.clientapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josewynder.neoapp.clientapi.dto.ClientRequestDTO;
import com.josewynder.neoapp.clientapi.model.Client;
import com.josewynder.neoapp.clientapi.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        client = Client.builder()
                .name("José Wynder")
                .cpf("12345678900")
                .email("jose@neoapp.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .phone("12345678900")
                .build();

        clientRepository.save(client);
    }


    @Test
    @WithMockUser
    void createClientSuccess() throws Exception {
        ClientRequestDTO request = new ClientRequestDTO(
                "Maria",
                "00987654321",
                LocalDate.of(2000, 1, 1),
                "maria@neoapp.com",
                "00987654321"
        );

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Maria")))
                .andExpect(jsonPath("$.cpf", is("00987654321")))
                .andExpect(jsonPath("$.email", is("maria@neoapp.com")));
    }


    @Test
    @WithMockUser
    void getClientByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(client.getName())))
                .andExpect(jsonPath("$.cpf", is(client.getCpf())));
    }


    @Test
    @WithMockUser
    void getAllClientsReturnsList() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is(client.getName())));
    }


    @Test
    @WithMockUser
    void updateClientSuccess() throws Exception {
        ClientRequestDTO updateRequest = new ClientRequestDTO(
                "José Updated",
                client.getCpf(),
                client.getBirthDate(),
                client.getEmail(),
                client.getPhone()
        );

        mockMvc.perform(put("/api/clients/{id}", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("José Updated")));
    }


    @Test
    @WithMockUser
    void deleteClientSuccess() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", client.getId()))
                .andExpect(status().isNoContent());
    }

}
