package com.example.provider.dao;

import com.example.provider.entities.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriasDao extends CrudRepository<Categoria, Long> {
}
