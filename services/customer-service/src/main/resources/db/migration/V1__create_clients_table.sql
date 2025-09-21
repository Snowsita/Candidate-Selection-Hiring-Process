/**
 * V1: Script inicial de la base de datos para el servicio de clientes.
 * Crea la tabla 'clients' que almacenar� la informaci�n de los clientes.
 *
 * Este script es ejecutado autom�ticamente por Flyway al iniciar la aplicaci�n.
 */

CREATE TABLE clients (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         age INT NOT NULL,
                         date_of_birth DATE NOT NULL
);