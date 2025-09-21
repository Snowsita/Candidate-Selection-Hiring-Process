package com.etorres.customermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class CustomerServiceApplicationTests {

	@Container
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	/**
	 * Sobrescribe dinámicamente las propiedades de la aplicación ANTES de que el contexto de Spring se inicie.
	 * Le decimos a Spring que use la URL, el usuario y la contraseña del contenedor
	 * de base de datos que acabamos de iniciar, en lugar de los valores de application.yml.
	 * @param registry el registro de propiedades dinámicas.
	 */
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mysqlContainer::getUsername);
		registry.add("spring.datasource.password", mysqlContainer::getPassword);
		registry.add("spring.flyway.enabled", () -> "true");
	}

	@Test
	void contextLoads() {
	}

}