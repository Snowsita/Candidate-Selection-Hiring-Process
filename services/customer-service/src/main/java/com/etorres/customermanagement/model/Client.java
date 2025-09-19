package com.etorres.customermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Primer nombre del cliente.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Apellido del cliente.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Edad del cliente.
     */
    @Column(name = "age", nullable = false)
    private Integer age;

    /**
     * Fecha de nacimiento del cliente.
     */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
}
