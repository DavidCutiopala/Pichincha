package com.pichincha.test.services;

import com.pichincha.test.dto.ClienteDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.excepions.EntityNotFoundException;
import com.pichincha.test.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity<?> crearCliente(ClienteDto clienteDto) {

        Cliente cliente = new Cliente();
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setEdad(clienteDto.getEdad());
        cliente.setGenero(clienteDto.getGenero());
        cliente.setIdentificacion(clienteDto.getIdentificacion());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setContrasena(clienteDto.getContrasena());
        cliente.setEstado(clienteDto.getEstado());
        cliente.setClienteid(clienteDto.getClienteid());
        clienteRepository.save(cliente);
        return new ResponseEntity<String>("Cliente Creado Correctamente", HttpStatus.CREATED);
    }

    public ResponseEntity<?> editarCliente(ClienteDto clienteDto) {

        Cliente cliente = clienteRepository.findByClienteid(clienteDto.getClienteid());
        if (cliente != null && cliente.getId() != null) {
            cliente.setDireccion(clienteDto.getDireccion() != null && clienteDto.getDireccion().compareTo("") != 0
                    ? clienteDto.getDireccion()
                    : cliente.getDireccion());
            cliente.setEdad(clienteDto.getEdad() != 0 ? clienteDto.getEdad() : cliente.getEdad());
            cliente.setGenero(
                    clienteDto.getGenero() != null && clienteDto.getGenero().compareTo("") != 0 ? clienteDto.getGenero()
                            : cliente.getGenero());
            cliente.setIdentificacion(
                    clienteDto.getIdentificacion() != null && clienteDto.getIdentificacion().compareTo("") != 0
                            ? clienteDto.getIdentificacion()
                            : cliente.getIdentificacion());
            cliente.setNombre(
                    clienteDto.getNombre() != null && clienteDto.getNombre().compareTo("") != 0 ? clienteDto.getNombre()
                            : cliente.getNombre());
            cliente.setTelefono(clienteDto.getTelefono() != null && clienteDto.getTelefono().compareTo("") != 0
                    ? clienteDto.getTelefono()
                    : cliente.getTelefono());
            cliente.setContrasena(clienteDto.getContrasena() != null && clienteDto.getContrasena().compareTo("") != 0
                    ? clienteDto.getContrasena()
                    : cliente.getContrasena());
            cliente.setEstado(
                    clienteDto.getEstado() != null && clienteDto.getEstado().compareTo("") != 0 ? clienteDto.getEstado()
                            : cliente.getEstado());
            clienteRepository.save(cliente);
            return new ResponseEntity<String>("Cliente Editado Correctamente", HttpStatus.OK);

        } else {
            throw new EntityNotFoundException(Cliente.class, "Cliente Id",
                    clienteDto.getClienteid() != null ? clienteDto.getClienteid() : "es nulo");
        }
    }

    public ResponseEntity<?> eliminarCliente(String clienteId) {

        Cliente cliente = clienteRepository.findByClienteid(clienteId);
        if (cliente != null && cliente.getId() != null) {
            clienteRepository.delete(cliente);
            return new ResponseEntity<String>("Cliente Eliminado Correctamente", HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(Cliente.class, "Cliente Id",
                    clienteId);
        }
    }

    public Cliente obtenerCliente(String clienteId) {
        Cliente cliente = clienteRepository.findByClienteid(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException(Cliente.class, "Cliente Id",
                    clienteId);
        }
        return cliente;
    }

}
