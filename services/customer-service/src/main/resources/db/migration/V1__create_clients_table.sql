/**
 * V1: Script inicial de la base de datos para el servicio de clientes.
 * Crea la tabla 'clients' que almacenará la información de los clientes.
 *
 * Este script es ejecutado automáticamente por Flyway al iniciar la aplicación.
 */

CREATE TABLE clients (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         age INT NOT NULL,
                         date_of_birth DATE NOT NULL
);