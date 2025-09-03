package com.josewynder.neoapp.clientapi.service;

import com.josewynder.neoapp.clientapi.exception.ClientDuplicateException;
import com.josewynder.neoapp.clientapi.exception.ClientNotFoundException;
import com.josewynder.neoapp.clientapi.model.Client;
import com.josewynder.neoapp.clientapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.josewynder.neoapp.clientapi.specification.ClientSpecification.*;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client) {
        if (clientRepository.existsByCpf(client.getCpf())) {
            throw new ClientDuplicateException("cpf", client.getCpf());
        }
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ClientDuplicateException("email", client.getEmail());
        }
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (clientRepository.existsByEmail(updatedClient.getEmail())
                && !existing.getEmail().equals(updatedClient.getEmail())) {
            throw new ClientDuplicateException("email", updatedClient.getEmail());
        }

        if (clientRepository.existsByCpf(updatedClient.getCpf())
                && !existing.getCpf().equals(updatedClient.getCpf())) {
            throw new ClientDuplicateException("cpf", updatedClient.getCpf());
        }

        existing.setName(updatedClient.getName());
        existing.setEmail(updatedClient.getEmail());
        existing.setCpf(updatedClient.getCpf());
        existing.setBirthDate(updatedClient.getBirthDate());

        return clientRepository.save(existing);
    }


    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    public Page<Client> searchClients(String name, String cpf, LocalDate birthDate, String email, String phone, Pageable pageable) {
        Specification<Client> spec = Specification
                .allOf(hasName(name))
                .and(hasCpf(cpf))
                .and(hasBirthDate(birthDate))
                .and(hasEmail(email))
                .and(hasPhone(phone));

        return clientRepository.findAll(spec, pageable);
    }

}
