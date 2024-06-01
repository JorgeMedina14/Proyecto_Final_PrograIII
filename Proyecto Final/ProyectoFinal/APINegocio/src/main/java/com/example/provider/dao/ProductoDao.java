package com.example.provider.dao;

import com.example.provider.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto, Long> {
    public Producto findByNombre(String nombre);
}
