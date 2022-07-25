package com.pichincha.test.repositories;

import java.util.Optional;

import com.pichincha.test.entities.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByClienteid(String clienteId);
}
