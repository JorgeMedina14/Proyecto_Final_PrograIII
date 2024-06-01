package com.example.provider.data.services;

import com.example.provider.entities.CompraProducto;

import java.util.List;

public interface CompraProductoService {
    public CompraProducto saveCompraProducto(CompraProducto compraProducto);
    public List<CompraProducto> getAllCompraProductos();
    public CompraProducto getCompraProductoById(Long id);
    public void deleteCompraProducto(Long id);
    public CompraProducto updateCompraProducto(Long id, CompraProducto compraProducto);
}
