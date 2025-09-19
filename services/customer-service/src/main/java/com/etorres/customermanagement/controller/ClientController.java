package com.etorres.customermanagement.controller;

import com.etorres.customermanagement.dto.ClientDto;
import com.etorres.customermanagement.dto.CreateClientDto;
import com.etorres.customermanagement.dto.StatisticsDto;
import com.etorres.customermanagement.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Endpoint para crear un nuevo cliente.
     * HTTP Method: POST
     * URL: /api/v1/clients
     * @param createClientDto DTO con los datos del cliente a crear.
     * @return ResponseEntity con el cliente creado y el estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody CreateClientDto createClientDto) {
        ClientDto createdClient = clientService.createClient(createClientDto);
        return ResponseEntity.status(CREATED)
                .body(createdClient);
    }

    /**
     * Endpoint para obtener las estadísticas de los clientes.
     * HTTP Method: GET
     * URL: /api/v1/clients
     * @return ResponseEntity con las estadísticas de los clientes y el estado HTTP 200 (OK).
     */
    @GetMapping("/stats")
    public ResponseEntity<StatisticsDto> getClientStatistics() {
        StatisticsDto statistics = clientService.getClientStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Endpoint para obtener todos los clientes.
     * HTTP Method: GET
     * URL: /api/v1/clients
     * @return ResponseEntity con la lista de todos los clientes y el estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
}
