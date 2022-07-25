package com.pichincha.test.services;

import com.pichincha.test.dto.ClienteDto;
import com.pichincha.test.entities.Cliente;
import com.pichincha.test.exception.NotStoreException;
import com.pichincha.test.repositories.ClienteRepository;
import com.pichincha.test.services.interfaces.IClienteService;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : David Cutiopala
 * @date : 24 de Julio del 2022
 * @description: Servicio para gestionar funcionalidades del cliente.
 */

@Service
@Slf4j
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @author : David Cutiopala
     * @date : 24 de Julio del 2022
     * @description: Función para crear un cliente.
     * @param ClienteDto Objeto que contiene los datos de entrada
     * @return Client Objeto que contiene solo la informacion del cliente
     */

    public Cliente crearCliente(ClienteDto clienteDto) {

        log.info("Creando cliente");
        Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente updateClient(ClienteDto clienteDto) {

        log.info("Actualizando cliente");
        getClientById(clienteDto.getClienteid());
        Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        clienteRepository.save(cliente);
        return cliente;
    }

    @Transactional
    public Boolean eliminarCliente(String clienteId) {
        log.info("Eliminando cliente");
        Boolean clienteDelete = false;

        Cliente cliente = getClientById(clienteId);
        clienteRepository.delete(cliente);
        return clienteDelete;

    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getClientById(String clienteId) {

        log.info("Consultando cliente");
        Optional<Cliente> cliente = clienteRepository.findByClienteid(clienteId);
        if (!cliente.isPresent()) {
            throw new NotStoreException("No existe el cliente con ID: " + clienteId);
        }
        return cliente.get();
    }

}
