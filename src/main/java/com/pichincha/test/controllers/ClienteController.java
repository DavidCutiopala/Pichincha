package com.pichincha.test.controllers;

import com.pichincha.test.dto.ClienteDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.services.ClienteService;

import javax.validation.Valid;

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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/test/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "creación de un cliente.", notes = "<b>Ejemplo de envío</b></br>URL -> localhost:8082/")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cliente creado ."), })
	@PostMapping("/crear")
	public Cliente crearCliente(@Valid @RequestBody ClienteDto clienteDto) {
		return clienteService.crearCliente(clienteDto);
	}

	@ApiOperation(value = "edicion de un cliente.", notes = "<b>Ejemplo de envío</b></br>URL -> localhost:8082/")
	@PutMapping("/edit")
	public Cliente editarCliente(@Valid @RequestBody ClienteDto clienteDto) {
		return clienteService.updateClient(clienteDto);
	}

	@ApiOperation(value = "eliminar  un cliente.")
	@DeleteMapping("/delete/{clienteId}")
	public ResponseEntity<?> eliminarCliente(@PathVariable("clienteId") String clienteId) {
		Boolean respuesta = clienteService.eliminarCliente(clienteId);
		return Boolean.TRUE.equals(respuesta) ? ResponseEntity.ok(clienteService.eliminarCliente(clienteId))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "obtener un cliente.")
	@GetMapping("/get/{clienteId}")
	public Cliente obtenerCliente(@PathVariable("clienteId") String clienteId) {
		return clienteService.getClientById(clienteId);
	}

}
