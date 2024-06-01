package com.example.provider.data.services;

import com.example.provider.entities.Categoria;
import com.example.provider.entities.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);
    public Producto findByNombre(String nombre);
    public Producto save(Producto producto);
    public void delete(Long id);
    public Producto update(Long id, Producto producto);
    public List<Categoria> findAllCategorias();
    public Categoria findCategoriaById(Long id);
    public Categoria saveCategoria(Categoria categoria);
    public void deleteCategoria(Long id);
}
