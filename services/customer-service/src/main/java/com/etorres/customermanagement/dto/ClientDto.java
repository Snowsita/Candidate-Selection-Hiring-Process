package com.etorres.customermanagement.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar la información de un cliente.
 * @param id
 * @param name
 * @param lastName
 * @param age
 * @param dateOfBirth
 */
public record ClientDto(
        Long id,
        String name,
        String lastName,
        int age,
        LocalDate dateOfBirth,
        LocalDate estimatedLifeExpectancyDate
) {
}
