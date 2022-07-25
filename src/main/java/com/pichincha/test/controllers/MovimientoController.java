package com.pichincha.test.controllers;

import java.text.ParseException;

import com.pichincha.test.dto.MovimientoDto;
import com.pichincha.test.entities.Movimiento;
import com.pichincha.test.services.MovimientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/test/movimientos")
public class MovimientoController {

	@Autowired
	private MovimientoService movimientoService;

	@ApiOperation(value = "creacion de un movimiento.")
	@PutMapping("/put")
	public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDto MovimientoDto) throws ParseException {
		return movimientoService.crearMovimiento(MovimientoDto);
	}

	@ApiOperation(value = "edicion de un Movimiento.")
	@PostMapping("/post")
	public ResponseEntity<?> editarMovimiento(@RequestBody MovimientoDto movimientoDto) throws ParseException {
		return movimientoService.editarMovimiento(movimientoDto);
	}

	@ApiOperation(value = "eliminar  un Movimiento.")
	@DeleteMapping("/delete/{movimientoId}")
	public ResponseEntity<?> eliminarMovimiento(@PathVariable("movimientoId") Long movimientoId) {
		return movimientoService.eliminarMovimiento(movimientoId);
	}

	@ApiOperation(value = "obtener un Movimiento.")
	@GetMapping("/get/{movimientoId}")
	public Movimiento obtenerMovimiento(@PathVariable("movimientoId") Long movimientoId) {
		return movimientoService.obtenerMovimiento(movimientoId);
	}

	@ApiOperation(value = "agregar y editar Movimientos.")
	@PostMapping("/registerMovements")
	public ResponseEntity<?> registarMovimientos(@RequestBody MovimientoDto movimientoDto) throws ParseException {
		return movimientoService.registarMovimientos(movimientoDto);
	}

}
