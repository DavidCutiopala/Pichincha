package com.pichincha.test.controllers;

import com.pichincha.test.dto.ClienteDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.services.ClienteService;

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
@RequestMapping(value = "/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "creacion de un cliente.")
	@PutMapping("/put")
	public ResponseEntity<?> crearCliente(@RequestBody ClienteDto clienteDto) {
		return clienteService.crearCliente(clienteDto);
	}

	@ApiOperation(value = "edicion de un cliente.")
	@PostMapping("/post")
	public ResponseEntity<?> editarCliente(@RequestBody ClienteDto clienteDto) {
		return clienteService.editarCliente(clienteDto);
	}

	@ApiOperation(value = "eliminar  un cliente.")
	@DeleteMapping("/delete/{clienteId}")
	public ResponseEntity<?> eliminarCliente(@PathVariable("clienteId") String clienteId) {
		return clienteService.eliminarCliente(clienteId);
	}

	@ApiOperation(value = "obtener un cliente.")
	@GetMapping("/get/{clienteId}")
	public Cliente obtenerCliente(@PathVariable("clienteId") String clienteId) {
		return clienteService.obtenerCliente(clienteId);
	}

}
