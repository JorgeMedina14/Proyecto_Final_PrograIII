package com.example.consumer.data.services;

import com.example.consumer.dao.CarritoDeComprasDao;
import com.example.consumer.dao.CarritoProductoDao;
import com.example.consumer.dao.ClienteDao;
import com.example.consumer.entities.CarritoDeCompras;
import com.example.consumer.entities.CarritoProducto;
import com.example.consumer.entities.Cliente;
import com.example.consumer.entities.Producto;
import com.example.provider.data.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoDeComprasServiceImpl implements CarritoDeComprasService {
    @Autowired
    private CarritoDeComprasDao carritoDeComprasDao;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CarritoProductoDao carritoProductoDao;

    @Override
    public List<CarritoDeCompras> getAll() {
        return (List<CarritoDeCompras>) carritoDeComprasDao.findAll();
    }

    @Override
    public CarritoDeCompras getCarritoById(Long id) {
        return carritoDeComprasDao.findById(id).orElse(null);
    }

    @Override
    public CarritoDeCompras getCarritoByUsuarioId(Long usuarioId) {
        CarritoDeCompras carrito = carritoDeComprasDao.findCarritoDeComprasByCliente_Id(usuarioId);
        if (carrito != null) return carrito;
        Cliente cliente = clienteDao.findById(usuarioId).orElse(null);
        if (cliente != null) {
            carrito = new CarritoDeCompras();
            carrito.setCliente(cliente);
            return carritoDeComprasDao.save(carrito);
        }
        return null;
    }

    @Override
    public CarritoDeCompras saveCarrito(CarritoDeCompras carrito) {
        return carritoDeComprasDao.save(carrito);
    }

    @Override
    public CarritoDeCompras updateCarrito(Long id, CarritoDeCompras carrito) {
        if (carritoDeComprasDao.existsById(id)) {
            carrito.setId(id);
            return carritoDeComprasDao.save(carrito);
        }
        return null;
    }

    @Override
    public CarritoDeCompras updateByCliente(CarritoDeCompras carritoDeCompras) {
        Cliente cliente = carritoDeCompras.getCliente();
        if (cliente != null) {
            Long clienteId = cliente.getId();
            CarritoDeCompras carrito = carritoDeComprasDao.findCarritoDeComprasByCliente_Id(clienteId);
            if (carrito != null) {
                carritoDeCompras.setId(carrito.getId());
            }
            carritoProductoDao.saveAll(carritoDeCompras.getProductos());
            return carritoDeComprasDao.save(carritoDeCompras);
        } else {
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
    }

    @Override
    public void deleteCarrito(Long id) {
        carritoDeComprasDao.deleteById(id);
    }
}
