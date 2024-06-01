package com.example.provider.data.services;

import com.example.provider.dao.CompraProductoDao;
import com.example.provider.entities.CompraProducto;
import com.example.provider.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraProductoServiceImpl implements CompraProductoService {

    @Autowired
    private CompraProductoDao compraProductoDao;

    @Autowired
    private ProductoService productoService;

    @Override
    public CompraProducto saveCompraProducto(CompraProducto compraProducto) {
        Producto producto = productoService.findByNombre(compraProducto.getProducto());
        producto.setStock(producto.getStock() + compraProducto.getCantidad());
        productoService.update(producto.getId(), producto);
        return compraProductoDao.save(compraProducto);
    }

    @Override
    public List<CompraProducto> getAllCompraProductos() {
        return (List<CompraProducto>) compraProductoDao.findAll();
    }

    @Override
    public CompraProducto getCompraProductoById(Long id) {
        return compraProductoDao.findById(id).orElse(null);
    }

    @Override
    public void deleteCompraProducto(Long id) {
        if (compraProductoDao.existsById(id)) {
            compraProductoDao.findById(id).ifPresent(c -> {
                Producto producto = productoService.findByNombre(c.getProducto());
                producto.setStock(producto.getStock() - c.getCantidad());
                productoService.update(producto.getId(), producto);
            });

            compraProductoDao.deleteById(id);
        } else {
            throw new RuntimeException("Compra de Producto no encontrada");
        }
    }

    @Override
    public CompraProducto updateCompraProducto(Long id, CompraProducto compraProducto) {
        if (compraProductoDao.existsById(id)) {
            compraProductoDao.findById(id).ifPresent(c -> {
                Producto producto = productoService.findByNombre(c.getProducto());
                producto.setStock(producto.getStock() - c.getCantidad());
                productoService.update(producto.getId(), producto);
            });
            compraProducto.setId(id);
            Producto producto = productoService.findByNombre(compraProducto.getProducto());
            producto.setStock(producto.getStock() + compraProducto.getCantidad());
            productoService.update(producto.getId(), producto);
            return compraProductoDao.save(compraProducto);
        } else {
            throw new RuntimeException("Compra de Producto no encontrada");
        }
    }
}
