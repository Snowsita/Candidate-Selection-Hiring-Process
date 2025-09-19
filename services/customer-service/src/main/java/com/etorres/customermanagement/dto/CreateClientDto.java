package com.etorres.customermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para crear un nuevo cliente.
 * @param firstName
 * @param lastName
 * @param age
 * @param dateOfBirth
 */
public record CreateClientDto(
        @NotBlank(message = "El nombre es obligatorio")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotNull(message = "La edad es obligatoria")
        @Positive(message = "La edad debe ser un número positivo")
        Integer age,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
        LocalDate dateOfBirth
) {
}
