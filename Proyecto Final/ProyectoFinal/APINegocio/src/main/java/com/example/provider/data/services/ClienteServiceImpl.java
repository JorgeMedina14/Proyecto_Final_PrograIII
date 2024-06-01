package com.example.provider.data.services;

import com.example.provider.dao.ClienteDao;
import com.example.provider.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    public Cliente getClienteByCodigo(String codigo) {
        return clienteDao.findByCodigo(codigo);
    }

    @Override
    public void deleteCliente(Long id) {
        if (clienteDao.existsById(id)) {
            clienteDao.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    @Override
    public void updateCliente(Long id, Cliente cliente) {
        if (clienteDao.existsById(id)) {
            cliente.setId(id);
            clienteDao.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }
}
