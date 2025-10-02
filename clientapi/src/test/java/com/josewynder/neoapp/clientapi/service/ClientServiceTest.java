package com.josewynder.neoapp.clientapi.service;

import com.josewynder.neoapp.clientapi.exception.ClientDuplicateException;
import com.josewynder.neoapp.clientapi.exception.ClientNotFoundException;
import com.josewynder.neoapp.clientapi.model.Client;
import com.josewynder.neoapp.clientapi.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = Client.builder()
                .id(1L)
                .name("José Wynder")
                .cpf("12345678900")
                .email("jose@neoapp.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .phone("12345678900")
                .build();
    }


    @Test
    void createClientSuccess() {
        when(clientRepository.existsByCpf(client.getCpf())).thenReturn(false);
        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        Client created = clientService.createClient(client);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo(client.getName());
        verify(clientRepository).save(client);
    }

    @Test
    void createClientDuplicateCpfThrowsException() {
        when(clientRepository.existsByCpf(client.getCpf())).thenReturn(true);

        assertThrows(ClientDuplicateException.class, () -> clientService.createClient(client));
        verify(clientRepository, never()).save(any());
    }

    @Test
    void createClientDuplicateEmailThrowsException() {
        when(clientRepository.existsByCpf(client.getCpf())).thenReturn(false);
        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(true);

        assertThrows(ClientDuplicateException.class, () -> clientService.createClient(client));
        verify(clientRepository, never()).save(any());
    }


    @Test
    void getClientByIdSuccess() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client found = clientService.getClientById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void getClientByIdNotFoundThrowsException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(1L));
    }


    @Test
    void getAllClientsReturnsPage() {
        Page<Client> page = new PageImpl<>(Arrays.asList(client));
        when(clientRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<Client> result = clientService.getAllClients(PageRequest.of(0, 10));

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }


    @Test
    void updateClientSuccess() {
        Client updated = Client.builder()
                .name("José Updated")
                .cpf(client.getCpf())
                .email(client.getEmail())
                .birthDate(client.getBirthDate())
                .phone(client.getPhone())
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail(updated.getEmail())).thenReturn(false);
        when(clientRepository.existsByCpf(updated.getCpf())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(updated);

        Client result = clientService.updateClient(1L, updated);

        assertThat(result.getName()).isEqualTo("José Updated");
        verify(clientRepository).save(client);
    }

    @Test
    void updateClientDuplicateEmailThrowsException() {
        Client updated = Client.builder().email("wynder@neoapp.com").cpf(client.getCpf()).build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail("wynder@neoapp.com")).thenReturn(true);

        assertThrows(ClientDuplicateException.class, () -> clientService.updateClient(1L, updated));
        verify(clientRepository, never()).save(any());
    }

    @Test
    void updateClientNotFoundThrowsException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(1L, client));
    }


    @Test
    void deleteClientSuccess() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    void deleteClientNotFoundThrowsException() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(1L));
        verify(clientRepository, never()).deleteById(any());
    }
}
