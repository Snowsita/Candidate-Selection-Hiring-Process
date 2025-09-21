package com.etorres.customermanagement.service;

import com.etorres.customermanagement.dto.ClientDto;
import com.etorres.customermanagement.dto.CreateClientDto;
import com.etorres.customermanagement.dto.StatisticsDto;
import com.etorres.customermanagement.model.Client;
import com.etorres.customermanagement.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = Client.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .age(30)
                .dateOfBirth(LocalDate.of(1994, 8, 15))
                .build();

        client2 = Client.builder()
                .id(2L)
                .firstName("Ana")
                .lastName("Gomez")
                .age(40)
                .dateOfBirth(LocalDate.of(1984, 5, 20))
                .build();
    }

    @DisplayName("Prueba para crear un cliente exitosamente")
    @Test
    void testCreateClient_shouldReturnSavedClientDto() {
        CreateClientDto createDto = new CreateClientDto(
                "Juan",
                "Perez",
                30,
                LocalDate.of(1994, 8, 15)
        );

        when(clientRepository.save(any(Client.class))).thenReturn(client1);

        ClientDto resultDto = clientService.createClient(createDto);

        assertNotNull(resultDto);
        assertEquals(client1.getId(), resultDto.id());
        assertEquals("Juan", resultDto.firstName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    /**
     * Test para verificar que el método getAllClients devuelve una lista de ClientDto.
     */
    @DisplayName("Prueba para listar todos los clientes")
    @Test
    void testGetAllClients_shouldReturnListOfClientDtos() {
        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        List<ClientDto> resultList = clientService.getAllClients();

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals("Juan", resultList.get(0).firstName());
        assertEquals("Ana", resultList.get(1).firstName());
        verify(clientRepository, times(1)).findAll();
    }

    @DisplayName("Prueba para obtener estadísticas de clientes")
    @Test
    void testGetClientStatistics_whenNoClients_shouldReturnZeros() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        StatisticsDto stats = clientService.getClientStatistics();

        assertNotNull(stats);
        assertEquals(0.0, stats.averageAge());
        assertEquals(0.0, stats.standardDeviationAge());
        verify(clientRepository, times(1)).findAll();
    }
}
