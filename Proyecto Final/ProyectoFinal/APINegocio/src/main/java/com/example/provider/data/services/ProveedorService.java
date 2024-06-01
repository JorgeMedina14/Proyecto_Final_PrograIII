package com.example.provider.data.services;

import com.example.provider.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    public Proveedor saveProveedor(Proveedor proveedor);
    public List<Proveedor> getProveedores();
    public Proveedor getProveedorById(Long id);
    public void deleteProveedor(Long id);
    public void updateProveedor(Long id, Proveedor proveedor);
}
