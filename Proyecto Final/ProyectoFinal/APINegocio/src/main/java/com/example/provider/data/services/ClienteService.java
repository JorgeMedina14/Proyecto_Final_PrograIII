package com.example.provider.data.services;

import com.example.provider.entities.Cliente;

import java.util.List;

public interface ClienteService {
    public Cliente saveCliente(Cliente cliente);
    public List<Cliente> getAllClientes();
    public Cliente getClienteById(Long id);
    public Cliente getClienteByCodigo(String codigo);
    public void deleteCliente(Long id);
    public void updateCliente(Long id, Cliente cliente);
}
