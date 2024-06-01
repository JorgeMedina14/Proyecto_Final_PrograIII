package com.example.consumer.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CarritoDeCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @OneToMany
    private List<CarritoProducto> productos;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CarritoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<CarritoProducto> productos) {
        this.productos = productos;
    }

    public String toString() {
        return "CarritoDeCompras{" +
                "id=" + id +
                ", cliente=" + cliente.toString() +
                ", productos=" + productos +
                '}';
    }
}
