package com.josewynder.neoapp.clientapi.controller;

import com.josewynder.neoapp.clientapi.dto.ClientRequestDTO;
import com.josewynder.neoapp.clientapi.dto.ClientResponseDTO;
import com.josewynder.neoapp.clientapi.dto.ErrorResponseDTO;
import com.josewynder.neoapp.clientapi.mapper.ClientMapper;
import com.josewynder.neoapp.clientapi.model.Client;
import com.josewynder.neoapp.clientapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Manage clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Client already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.toEntity(clientRequestDTO);
        Client savedClient = clientService.createClient(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedClient.getId())
                .toUri();

        return ResponseEntity.created(location).body(clientMapper.toDTO(savedClient));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    @GetMapping
    @Operation(summary = "Get all clients (paginated)")
    public ResponseEntity<Page<ClientResponseDTO>> getAllClients(@ParameterObject Pageable pageable) {
        Page<ClientResponseDTO> clients = clientService.getAllClients(pageable)
                .map(clientMapper::toDTO);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/search")
    @Operation(summary = "Search clients by attributes")
    public ResponseEntity<Page<ClientResponseDTO>> searchClients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false, value = "birth-date") LocalDate birthDate,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @ParameterObject Pageable pageable
    ) {
        Page<ClientResponseDTO> result = clientService
                .searchClients(name, cpf, birthDate, email, phone, pageable)
                .map(clientMapper::toDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully",
                    content = @Content(schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO clientRequestDTO
    ) {
        Client clientUpdate = clientMapper.toEntity(clientRequestDTO);
        Client updated = clientService.updateClient(id, clientUpdate);
        return ResponseEntity.ok(clientMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
