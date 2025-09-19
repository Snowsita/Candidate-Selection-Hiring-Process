package com.etorres.customermanagement.service;

import com.etorres.customermanagement.dto.ClientDto;
import com.etorres.customermanagement.dto.CreateClientDto;
import com.etorres.customermanagement.dto.StatisticsDto;

import java.util.List;

public interface ClientService {
    /**
     * Crea un nuevo cliente en el sistema.
     * @param createClientDto DTO con la información para crear el cliente.
     * @return El DTO del cliente recién creado, incluyendo su ID.
     */
    ClientDto createClient(CreateClientDto createClientDto);

    /**
     * Obtiene las estadísticas de los clientes en el sistema.
     * @return Un DTO con el promedio de edad y la desviación estándar de las edades.
     */
    StatisticsDto getClientStatistics();

    /**
     * Obtiene todos los clientes en el sistema.
     * @return Una lista de DTOs de clientes, cada uno con su fecha estimada de esperanza de vida calculada.
     */
    List<ClientDto> getAllClients();
}
