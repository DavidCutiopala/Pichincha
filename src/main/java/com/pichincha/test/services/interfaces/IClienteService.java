package com.pichincha.test.services.interfaces;

import com.pichincha.test.dto.ClienteDto;
import com.pichincha.test.entities.Cliente;

public interface IClienteService {

    Cliente getClientById(String clienteId);
    Cliente crearCliente(ClienteDto clienteDto);
    Cliente updateClient(ClienteDto clienteDto);
    Boolean eliminarCliente(String clienteId);
}
