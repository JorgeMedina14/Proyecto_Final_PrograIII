package com.example.consumer.data.services;

import com.example.consumer.entities.CarritoDeCompras;

import java.util.List;

public interface CarritoDeComprasService {
    public List<CarritoDeCompras> getAll();
    public CarritoDeCompras getCarritoById(Long id);
    public CarritoDeCompras getCarritoByUsuarioId(Long usuarioId);
    public CarritoDeCompras saveCarrito(CarritoDeCompras carrito);
    public CarritoDeCompras updateCarrito(Long id, CarritoDeCompras carrito);
    public CarritoDeCompras updateByCliente(CarritoDeCompras carritoDeCompras);
    public void deleteCarrito(Long id);
}
