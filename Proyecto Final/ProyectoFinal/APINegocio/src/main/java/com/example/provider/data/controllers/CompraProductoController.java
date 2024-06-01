package com.example.provider.data.controllers;

import com.example.provider.data.services.CompraProductoService;
import com.example.provider.entities.CompraProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra_producto")
public class CompraProductoController {

    @Autowired
    private CompraProductoService compraProductoService;

    @GetMapping
    public List<CompraProducto> getCompraProductos() {
        return compraProductoService.getAllCompraProductos();
    }

    @GetMapping("/{id}")
    public CompraProducto getCompraProductoById(@PathVariable Long id) {
        return compraProductoService.getCompraProductoById(id);
    }

    @PostMapping
    public CompraProducto saveCompraProducto(@RequestBody CompraProducto compraProducto) {
        return compraProductoService.saveCompraProducto(compraProducto);
    }

    @PutMapping("/{id}")
    public CompraProducto updateCompraProducto(@PathVariable Long id, @RequestBody CompraProducto compraProducto) {
        return compraProductoService.updateCompraProducto(id, compraProducto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompraProducto(@PathVariable Long id) {
        compraProductoService.deleteCompraProducto(id);
    }
}
