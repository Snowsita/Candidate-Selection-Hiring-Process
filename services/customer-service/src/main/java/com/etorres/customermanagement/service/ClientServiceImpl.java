package com.etorres.customermanagement.service;

import com.etorres.customermanagement.dto.ClientDto;
import com.etorres.customermanagement.dto.CreateClientDto;
import com.etorres.customermanagement.dto.StatisticsDto;
import com.etorres.customermanagement.model.Client;
import com.etorres.customermanagement.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz ClientService.
 * Contiene la lógica de negocio principal para la gestión de clientes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private static final int LIFE_EXPECTANCY_YEARS = 72;

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public ClientDto createClient(CreateClientDto createClientDto) {
        log.info("Creating client for: {} {}", createClientDto.firstName(), createClientDto.lastName());

        Client clientToSave = Client.builder()
                .firstName(createClientDto.firstName())
                .lastName(createClientDto.lastName())
                .age(createClientDto.age())
                .dateOfBirth(createClientDto.dateOfBirth())
                .build();

        Client savedClient = clientRepository.save(clientToSave);
        log.info("Client created with ID: {}", savedClient.getId());

        return mapEntityToDto(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticsDto getClientStatistics() {
        log.info("Calculating client statistics");
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            log.warn("DEBUG: No clients found in the database.");
            return new StatisticsDto(0.0, 0.0);
        }

        List<Integer> ages = clients.stream()
                .map(Client::getAge)
                .toList();

        double averageAge = ages.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double variance = ages.stream()
                .mapToDouble(age -> Math.pow(age - averageAge, 2))
                .average()
                .orElse(0.0);

        double standardDeviation = Math.sqrt(variance);

        log.info("INF: Cálculo de estadísticas completado. Promedio de edad: {}, Desviación estándar: {}", averageAge, standardDeviation);
        return new StatisticsDto(averageAge, standardDeviation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {
        log.info("INF: Solicitud para listar todos los clientes.");
        List<Client> clients = clientRepository.findAll();

        List<ClientDto> clientDtos = clients.stream()
                .map(this::mapEntityToDto)
                .toList();

        log.info("INF: Total de clientes encontrados: {}", clientDtos.size());
        return clientDtos;
    }

    private ClientDto mapEntityToDto(Client client) {
        LocalDate lifeExpectancyDate = client.getDateOfBirth().plusYears(LIFE_EXPECTANCY_YEARS);
        return new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAge(),
                client.getDateOfBirth(),
                lifeExpectancyDate
        );
    }
}
