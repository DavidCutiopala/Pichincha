package com.pichincha.test.repositories;

import java.util.List;

import com.pichincha.test.entities.Cliente;
import com.pichincha.test.entities.Cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findByIdCuenta(Long idCuenta);

    List<Cuenta> findByCliente(Cliente cliente);

    Cuenta findTopByOrderByIdCuentaDesc();

}
