package com.example.provider.dao;

import com.example.provider.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Long> {
    public Cliente findByCodigo(String codigo);
}
