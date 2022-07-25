package com.pichincha.test.services;

import java.math.BigDecimal;

import com.pichincha.test.dto.CuentaDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.exception.EntityNotFoundException;
import com.pichincha.test.repositories.CuentaRepository;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    public Cuenta crearCuenta(CuentaDto cuentaDto) {

        Cliente cliente = clienteService.getClientById(cuentaDto.getClienteId());
        if (cliente != null) {

            log.info("Creando cuenta bancaria");
            Cuenta cuenta = modelMapper.map(cuentaDto, Cuenta.class);
            return cuentaRepository.save(cuenta);
        } else {

            throw new EntityNotFoundException(Cliente.class, "Id cliente", cuentaDto.getClienteId());
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
