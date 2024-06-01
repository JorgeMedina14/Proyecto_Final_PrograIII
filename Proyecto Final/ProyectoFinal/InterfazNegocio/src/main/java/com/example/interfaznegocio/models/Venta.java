package com.example.interfaznegocio.models;

import java.util.Date;
import java.util.List;

public class Venta {
    private Long id;
    private List<VentaProducto> ventaProductos;
    private Long cliente;
    private String email;
    private Double totalVenta;
    private String fechaVenta;
    private String estado;

    public Venta() {
    }

    public Venta(List<VentaProducto> ventaProductos, Long cliente, String email, Double totalVenta, String fechaVenta, String estado) {
        this.ventaProductos = ventaProductos;
        this.cliente = cliente;
        this.email = email;
        this.totalVenta = totalVenta;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
    }

    public Venta(Long id, List<VentaProducto> ventaProductos, Long cliente, String email, Double totalVenta, String fechaVenta, String estado) {
        this.id = id;
        this.ventaProductos = ventaProductos;
        this.cliente = cliente;
        this.email = email;
        this.totalVenta = totalVenta;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VentaProducto> getVentaProductos() {
        return ventaProductos;
    }

    public void setVentaProductos(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
