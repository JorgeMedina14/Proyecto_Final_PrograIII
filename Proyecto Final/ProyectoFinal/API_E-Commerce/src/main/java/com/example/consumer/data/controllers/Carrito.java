package com.example.consumer.data.controllers;

import com.example.consumer.data.services.CarritoDeComprasService;
import com.example.consumer.entities.CarritoDeCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class Carrito {

    @Autowired
    private CarritoDeComprasService carritoDeComprasService;

    @GetMapping("/{id}")
    public CarritoDeCompras getCarritoByUsuarioId(@PathVariable Long id) {
        return carritoDeComprasService.getCarritoByUsuarioId(id);
    }

    @PostMapping
    public CarritoDeCompras saveCarrito(CarritoDeCompras carrito) {
        return carritoDeComprasService.saveCarrito(carrito);
    }

    @PutMapping
    public CarritoDeCompras updateByCliente(@RequestBody CarritoDeCompras carritoDeCompras) {
        return carritoDeComprasService.updateByCliente(carritoDeCompras);
    }

    @DeleteMapping
    public void deleteCarrito(Long id) {
        carritoDeComprasService.deleteCarrito(id);
    }
}
