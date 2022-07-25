package com.pichincha.test.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.pichincha.test.dto.ReporteDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.entities.Movimiento;
import com.pichincha.test.exception.EntityNotFoundException;
import com.pichincha.test.repositories.CuentaRepository;
import com.pichincha.test.repositories.MovimientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @Autowired
    private ClienteService clienteService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public List<ReporteDto> generarReporte(String fechaInicial, String fechaFin, String clienteId)
            throws ParseException {
        List<ReporteDto> listaReporte = null;
        if (validar(fechaInicial)) {
            if (validar(fechaFin)) {
                if (clienteId != null) {
                    Cliente cliente = clienteService.getClientById(clienteId);
                    if (cliente != null) {
                        List<Cuenta> cuentaLista = cuentaRepository.findByCliente(cliente);
                        if (cuentaLista != null && !cuentaLista.isEmpty()) {

                            List<Movimiento> listaMovimientos = movimientoRepository.getListMovimientoReporte(
                                    simpleDateFormat.parse(fechaInicial), simpleDateFormat.parse(fechaFin),
                                    cuentaLista);

                            listaReporte = generar(listaMovimientos);
                        } else {
                            throw new EntityNotFoundException(Cuenta.class, "Cliente Id",
                                    clienteId != null ? clienteId : "es nulo");
                        }
                    } else {
                        throw new EntityNotFoundException(Cliente.class, "Cliente Id",
                                clienteId != null ? clienteId : "es nulo");
                    }
                }
            }
        }
        return listaReporte;
    }

    public List<ReporteDto> generar(List<Movimiento> listaMovimientos) {
   
        if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
            return  listaMovimientos.stream()
            .map(mov -> {
                ReporteDto reporteDto = new ReporteDto();
                reporteDto.setFecha(simpleDateFormat.format(mov.getFecha()));
                reporteDto.setCliente(mov.getCuenta().getCliente().getNombre());
                reporteDto.setNumeroCuenta(mov.getCuenta().getNumeroCuenta());
                reporteDto.setTipo(mov.getCuenta().getTipoCuenta());
                reporteDto.setSaldoInicial(mov.getCuenta().getSaldoInicial());
                reporteDto.setEstado(
                        mov.getCuenta().getEstado() != null && mov.getCuenta().getEstado().compareTo("ACTIVO") == 0
                                ? true
                                : false);
                reporteDto.setMovimiento(mov.getValor());
                reporteDto.setSaldoDisponible(mov.getSaldo());

                return  reporteDto;
                
            })
            .collect(Collectors.toList());
        }else

        {
           return Collections.emptyList(); 
        }

    }

    public boolean validar(String valor) {
        return valor != null && valor.compareTo("") != 0 ? true : false;
    }

}
