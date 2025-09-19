package com.etorres.customermanagement.repository;

import com.etorres.customermanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
