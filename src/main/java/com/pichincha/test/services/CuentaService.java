package com.pichincha.test.services;

import java.math.BigDecimal;

import com.pichincha.test.dto.CuentaDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.excepions.EntityNotFoundException;
import com.pichincha.test.repositories.ClienteRepository;
import com.pichincha.test.repositories.CuentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity<?> crearCuenta(CuentaDto cuentaDto) {

        Cliente cliente = clienteRepository.findByClienteid(cuentaDto.getClienteId());
        if (cliente != null) {
            Cuenta cuenta = new Cuenta();
            cuenta.setCliente(cliente);
            cuenta.setEstado(cuentaDto.getEstado());
            cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
            cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
            cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());

            cuentaRepository.save(cuenta);
            return new ResponseEntity<String>("Cuenta Creada Correctamente", HttpStatus.CREATED);

        } else {
            throw new EntityNotFoundException(Cliente.class, "Cliente Id",
                    cuentaDto.getClienteId() != null ? cuentaDto.getClienteId() : "es nulo");
        }
    }

    public ResponseEntity<?> editarCuenta(CuentaDto cuentaDto) {

        Cuenta cuenta = cuentaRepository.findByIdCuenta(cuentaDto.getIdCuenta());
        if (cuenta != null) {
            cuenta.setEstado(
                    cuentaDto.getEstado() != null && cuentaDto.getEstado().compareTo("") != 0 ? cuentaDto.getEstado()
                            : cuenta.getEstado());
            cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta() != null && cuentaDto.getNumeroCuenta().compareTo("") != 0
                    ? cuentaDto.getNumeroCuenta()
                    : cuenta.getNumeroCuenta());
            cuenta.setSaldoInicial(
                    cuentaDto.getSaldoInicial() != null && cuentaDto.getSaldoInicial().compareTo(BigDecimal.ZERO) != 0
                            ? cuentaDto.getSaldoInicial()
                            : cuenta.getSaldoInicial());
            cuenta.setTipoCuenta(cuentaDto.getTipoCuenta() != null && cuentaDto.getTipoCuenta().compareTo("") != 0
                    ? cuentaDto.getTipoCuenta()
                    : cuenta.getTipoCuenta());
            cuentaRepository.save(cuenta);
            return new ResponseEntity<String>("Cuenta Editada Correctamente", HttpStatus.OK);

        } else {
            throw new EntityNotFoundException(Cuenta.class, "Cuenta Id",
                    cuentaDto.getIdCuenta() != null ? cuentaDto.getIdCuenta() : "es nulo");
        }
    }

    public ResponseEntity<?> eliminarCuenta(Long cuentaId) {

        Cuenta cuenta = cuentaRepository.findByIdCuenta(cuentaId);
        if (cuenta != null) {
            cuentaRepository.delete(cuenta);
            return new ResponseEntity<String>("Cuenta Eliminada Correctamente", HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(Cuenta.class, "Cuenta Id",
                    cuentaId != null ? cuentaId : "es nulo");
        }
    }

    public Cuenta obtenerCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findByIdCuenta(cuentaId);
        if (cuenta == null) {
            throw new EntityNotFoundException(Cuenta.class, "Cuenta Id",
                    cuentaId != null ? cuentaId : "es nulo");
        }
        return cuenta;
    }

}
