package com.example.provider.data.services;

import com.example.provider.dao.CategoriasDao;
import com.example.provider.dao.ProductoDao;
import com.example.provider.entities.Categoria;
import com.example.provider.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private CategoriasDao categoriasDao;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productoDao.findAll();
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return (List<Categoria>) categoriasDao.findAll();
    }

    @Override
    public Categoria findCategoriaById(Long id) {
        return categoriasDao.findById(id).orElse(null);
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        return categoriasDao.save(categoria);
    }

    @Override
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    public Producto findByNombre(String nombre) {
        // Devolver el primer producto con el nombre dado
        Producto producto = productoDao.findByNombre(nombre);
        if (producto != null) {
            return producto;
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    public Producto save(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public void delete(Long id) {
        if (productoDao.existsById(id)) {
            productoDao.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    public Producto update(Long id, Producto producto) {
        if (productoDao.existsById(id)) {
            producto.setId(id);
            return productoDao.save(producto);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    public void deleteCategoria(Long id) {
        if (categoriasDao.existsById(id)) {
            categoriasDao.deleteById(id);
        } else {
            throw new RuntimeException("Categoria no encontrada");
        }
    }
}
