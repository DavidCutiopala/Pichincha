package com.pichincha.test.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.pichincha.test.dto.MovimientoDto;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.entities.Movimiento;
import com.pichincha.test.exception.EntityNotFoundException;
import com.pichincha.test.exception.NotStoreException;
import com.pichincha.test.repositories.CuentaRepository;
import com.pichincha.test.repositories.MovimientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ResponseEntity<?> crearMovimiento(MovimientoDto movimientoDto) throws ParseException {

        Cuenta cuenta = cuentaRepository.findByIdCuenta(movimientoDto.getIdCuenta());
        if (cuenta != null) {
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setFecha(
                    movimientoDto.getFecha() != null ? formato.parse(movimientoDto.getFecha()) : null);
            movimiento.setSaldo(movimientoDto.getSaldo());
            movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
            movimiento.setValor(movimientoDto.getValor());

            movimientoRepository.save(movimiento);
            return new ResponseEntity<String>("Movimiento Creado Correctamente", HttpStatus.CREATED);

        } else {
            throw new EntityNotFoundException(Cuenta.class, "Cuenta Id",
                    movimientoDto.getIdCuenta() != null ? movimientoDto.getIdCuenta() : "es nulo");
        }
    }

    public ResponseEntity<?> editarMovimiento(MovimientoDto movimientoDto) throws ParseException {

        Movimiento movimiento = movimientoRepository.findByIdMovimiento(movimientoDto.getIdMovimiento());
        if (movimiento != null) {
            movimiento.setFecha(
                    movimientoDto.getFecha() != null ? formato.parse(movimientoDto.getFecha())
                            : movimiento.getFecha());
            movimiento.setSaldo(
                    movimientoDto.getSaldo() != null && movimientoDto.getSaldo().compareTo(BigDecimal.ZERO) != 0
                            ? movimientoDto.getSaldo()
                            : movimiento.getSaldo());
            movimiento.setTipoMovimiento(
                    movimientoDto.getTipoMovimiento() != null && movimientoDto.getTipoMovimiento().compareTo("") != 0
                            ? movimientoDto.getTipoMovimiento()
                            : movimiento.getTipoMovimiento());
            movimiento.setValor(
                    movimientoDto.getValor() != null && movimientoDto.getValor().compareTo(BigDecimal.ZERO) != 0
                            ? movimientoDto.getValor()
                            : movimiento.getValor());

            movimientoRepository.save(movimiento);
            return new ResponseEntity<String>("Movimiento Editado Correctamente", HttpStatus.CREATED);

        } else {
            throw new EntityNotFoundException(Movimiento.class, "Movimiento Id",
                    movimientoDto.getIdMovimiento() != null ? movimientoDto.getIdMovimiento() : "es nulo");
        }
    }

    public ResponseEntity<?> eliminarMovimiento(Long movimientoId) {

        Movimiento movimiento = movimientoRepository.findByIdMovimiento(movimientoId);
        if (movimiento != null) {
            movimientoRepository.delete(movimiento);
            return new ResponseEntity<String>("Movimiento Eliminado Correctamente", HttpStatus.CREATED);

        } else {
            throw new EntityNotFoundException(Movimiento.class, "Movimiento Id",
                    movimientoId != null ? movimientoId : "es nulo");
        }
    }

    public Movimiento obtenerMovimiento(Long movimientoId) {
        Movimiento movimiento = movimientoRepository.findByIdMovimiento(movimientoId);
        if (movimiento == null) {
            throw new EntityNotFoundException(Movimiento.class, "Movimiento Id",
                    movimientoId != null ? movimientoId : "es nulo");
        }
        return movimiento;
    }

    public ResponseEntity<?> registarMovimientos(MovimientoDto movimientoDto) throws ParseException {
        String mensaje = "";
        BigDecimal valorActual;

        mensaje = movimientoDto.getTipoMovimiento().compareTo("DEBITO") == 0
                ? validarLimiteDiario(movimientoDto.getFecha(), movimientoDto.getIdCuenta(),
                        movimientoDto.getValor())
                : "";
        valorActual = obtenerValorActual(movimientoDto.getIdCuenta(), movimientoDto.getSaldo());
        mensaje = mensaje.compareTo("") == 0 ? movimientoDto.getTipoMovimiento().compareTo("DEBITO") == 0
                ? validarDisponibilidadSaldo(valorActual, movimientoDto.getValor())
                : "" : mensaje;

        if (mensaje.compareTo("") == 0) {
            valorActual = movimientoDto.getTipoMovimiento().compareTo("CREDITO") == 0
                    ? valorActual.add(movimientoDto.getValor())
                    : movimientoDto.getTipoMovimiento().compareTo("DEBITO") == 0
                            ? valorActual.subtract(movimientoDto.getValor())
                            : valorActual;
            movimientoDto.setSaldo(valorActual);
            return crearMovimiento(movimientoDto);

        } else {
            throw new NotStoreException("No se pudo almacenar el movimiento: " + mensaje);

        }
    }

    public BigDecimal obtenerValorActual(Long cuentaId, BigDecimal valor) {
        BigDecimal valorActual = BigDecimal.ZERO;
        Cuenta cuenta = cuentaRepository.findByIdCuenta(cuentaId);
        if (cuenta != null) {
            List<Movimiento> movimientoLista = movimientoRepository.findByCuentaOrderByFechaDesc(cuenta);
            if (movimientoLista != null && !movimientoLista.isEmpty()) {
                valorActual = movimientoLista.get(0).getSaldo();
            } else {
                valorActual = valorActual.add(valor);

            }
        }
        return valorActual;
    }

    public String validarDisponibilidadSaldo(BigDecimal valorActual, BigDecimal valorTransaccion) {
        return valorActual.compareTo(valorTransaccion) >= 0 ? "" : "Saldo no disponible";
    }

    public String validarLimiteDiario(String fecha, Long cuentaId, BigDecimal valor) {
        Cuenta cuenta = cuentaRepository.findByIdCuenta(cuentaId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        BigDecimal totalDiario = BigDecimal.ZERO;
        if (cuenta != null) {
            List<Movimiento> movimientoLista;
            try {
                movimientoLista = movimientoRepository.findByFechaAndCuenta(simpleDateFormat.parse(fecha), cuenta);
                if (movimientoLista != null && !movimientoLista.isEmpty()) {
                    totalDiario = movimientoLista.stream().map(Movimiento::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    totalDiario = totalDiario.add(valor);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return totalDiario.compareTo(new BigDecimal(1000)) >= 0 ? "Cupo Diario Excedido." : "";
    }

}
