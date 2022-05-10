package com.pichincha.test.repositories;

import java.util.Date;
import java.util.List;

import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.entities.Movimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    Movimiento findByIdMovimiento(Long idMovimiento);

    List<Movimiento> findByCuentaOrderByFechaDesc(Cuenta cuenta);

    @Query(value = "SELECT * from movimiento m WHERE CAST(m.fecha as date)=:fecha and m.id_cuenta=:cuenta and m.tipo_movimiento='DEBITO' ", nativeQuery = true)
    List<Movimiento> findByFechaAndCuenta(@Param("fecha") Date fecha, @Param("cuenta") Cuenta cuenta);

    @Query(value = "SELECT * from movimiento m WHERE CAST(m.fecha as date) BETWEEN :fechaI and :fechaF and m.id_cuenta in :cuenta order by m.fecha desc", nativeQuery = true)
    List<Movimiento> getListMovimientoReporte(@Param("fechaI") Date fechaI, @Param("fechaF") Date fechaF,
            @Param("cuenta") List<Cuenta> cuenta);

    Movimiento findTopByOrderByIdMovimientoDesc();

}
