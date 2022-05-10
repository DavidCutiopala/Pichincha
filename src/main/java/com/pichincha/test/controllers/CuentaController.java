package com.pichincha.test.controllers;

import com.pichincha.test.dto.CuentaDto;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.services.CuentaService;

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
@RequestMapping(value = "/api/cuentas")
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;

	@ApiOperation(value = "creacion de una cuenta.")
	@PutMapping("/put")
	public ResponseEntity<?> crearCuenta(@RequestBody CuentaDto CuentaDto) {
		return cuentaService.crearCuenta(CuentaDto);
	}

	@ApiOperation(value = "edicion de una Cuenta.")
	@PostMapping("/post")
	public ResponseEntity<?> editarCuenta(@RequestBody CuentaDto cuentaDto) {
		return cuentaService.editarCuenta(cuentaDto);
	}

	@ApiOperation(value = "eliminar  una Cuenta.")
	@DeleteMapping("/delete/{cuentaId}")
	public ResponseEntity<?> eliminarCuenta(@PathVariable("cuentaId") Long cuentaId) {
		return cuentaService.eliminarCuenta(cuentaId);
	}

	@ApiOperation(value = "obtener un Cuenta.")
	@GetMapping("/get/{cuentaId}")
	public Cuenta obtenerCuenta(@PathVariable("cuentaId") Long cuentaId) {
		return cuentaService.obtenerCuenta(cuentaId);
	}

}
