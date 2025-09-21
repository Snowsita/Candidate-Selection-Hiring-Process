package com.etorres.customermanagement.controller;

import com.etorres.customermanagement.AbstractIntegrationTest;
import com.etorres.customermanagement.dto.CreateClientDto;
import com.etorres.customermanagement.model.Client;
import com.etorres.customermanagement.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
public class ClientControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
    }

    @DisplayName("POST /api/v1/clients - Crear cliente exitosamente")
    @Test
    @WithMockUser(username = "user", password = "password")
    void testCreateClient_whenValidInput_shouldReturn201Created() throws Exception {
        CreateClientDto createDto = new CreateClientDto("Juan", "Perez", 30, LocalDate.of(1994, 8, 15));
        String createDtoJson = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Juan")))
                .andExpect(jsonPath("$.lastName", is("Perez")));
    }

    @DisplayName("POST /api/v1/clients - Devolver 422 con datos inválidos")
    @Test
    @WithMockUser(username = "user", password = "password")
    void testCreateClient_whenInvalidInput_shouldReturn422() throws Exception {
        CreateClientDto createDto = new CreateClientDto(null, "", -5, LocalDate.now().plusDays(1));
        String createDtoJson = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details.firstName", is("El nombre es obligatorio")))
                .andExpect(jsonPath("$.details.lastName", is("El apellido es obligatorio")))
                .andExpect(jsonPath("$.details.age", is("La edad debe ser un número positivo")))
                .andExpect(jsonPath("$.details.dateOfBirth", is("La fecha de nacimiento debe ser una fecha pasada")));
    }

    @DisplayName("GET /api/v1/clients - Devolver lista de clientes")
    @Test
    @WithMockUser(username = "user", password = "password")
    void testGetAllClients_shouldReturnListOfClients() throws Exception {
        clientRepository.save(Client.builder().firstName("Ana").lastName("Gomez").age(40).dateOfBirth(LocalDate.of(1984, 5, 20)).build());

        mockMvc.perform(get("/api/v1/clients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("Ana")));
    }

    @DisplayName("GET /api/v1/clients/stats - Devolver estadísticas correctas")
    @Test
    @WithMockUser(username = "user", password = "password")
    void testGetClientStatistics_shouldReturnCorrectStats() throws Exception {
        clientRepository.save(Client.builder().firstName("Juan").lastName("Perez").age(30).dateOfBirth(LocalDate.now().minusYears(30)).build());
        clientRepository.save(Client.builder().firstName("Ana").lastName("Gomez").age(40).dateOfBirth(LocalDate.now().minusYears(40)).build());

        mockMvc.perform(get("/api/v1/clients/stats"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge", is(35.0)))
                .andExpect(jsonPath("$.standardDeviationAge", is(5.0)));
    }

    @DisplayName("GET /api/v1/clients - Devolver 401 sin autenticación")
    @Test
    void testGetAllClients_whenNoAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/clients"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
