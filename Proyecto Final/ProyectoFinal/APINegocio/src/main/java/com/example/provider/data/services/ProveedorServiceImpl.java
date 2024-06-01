package com.example.provider.data.services;

import com.example.provider.dao.ProveedorDao;
import com.example.provider.entities.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorDao proveedorDao;

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        return proveedorDao.save(proveedor);
    }

    @Override
    public List<Proveedor> getProveedores() {
        return (List<Proveedor>) proveedorDao.findAll();
    }

    @Override
    public Proveedor getProveedorById(Long id) {
        return proveedorDao.findById(id).orElse(null);
    }

    @Override
    public void deleteProveedor(Long id) {
        if (proveedorDao.existsById(id)) {
            proveedorDao.deleteById(id);
        } else {
            throw new RuntimeException("Proveedor no encontrado");
        }
    }

    @Override
    public void updateProveedor(Long id, Proveedor proveedor) {
        if (proveedorDao.existsById(id)) {
            proveedor.setId(id);
            proveedorDao.save(proveedor);
        } else {
            throw new RuntimeException("Proveedor no encontrado");
        }
    }
}
