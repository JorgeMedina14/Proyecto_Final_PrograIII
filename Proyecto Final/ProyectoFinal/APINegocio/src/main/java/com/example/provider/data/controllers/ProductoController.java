package com.example.provider.data.controllers;

import com.example.provider.data.services.ProductoService;
import com.example.provider.entities.Categoria;
import com.example.provider.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getProducto(){
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable Long id){
        return productoService.findById(id);
    }

    @PostMapping
    public Producto saveProducto(@RequestBody Producto producto){
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id){
        productoService.delete(id);
    }


    @GetMapping("/categorias")
    public List<Categoria> getCategorias(){
        return productoService.findAllCategorias();
    }

    @GetMapping("/categorias/{id}")
    public Categoria getCategoriaById(@PathVariable Long id){
        return productoService.findCategoriaById(id);
    }

    @PostMapping("/categorias")
    public Categoria saveCategoria(@RequestBody Categoria categoria){
        return productoService.saveCategoria(categoria);
    }

    @DeleteMapping("/categorias/{id}")
    public void deleteCategoria(@PathVariable Long id){
        productoService.deleteCategoria(id);
    }
}
