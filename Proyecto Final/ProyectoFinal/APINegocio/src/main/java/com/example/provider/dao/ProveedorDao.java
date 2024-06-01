package com.example.provider.dao;

import com.example.provider.entities.Proveedor;
import org.springframework.data.repository.CrudRepository;

public interface ProveedorDao extends CrudRepository<Proveedor, Long> {
}
