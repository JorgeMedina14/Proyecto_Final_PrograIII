package com.example.interfaznegocio.models;

public class VentaProducto {
    private Producto producto;
    private String proveedor;
    private Integer cantidad;
    private Double subtotal;

    public VentaProducto() {
    }

    public VentaProducto(Producto producto, String proveedor, Integer cantidad, Double subtotal) {
        this.producto = producto;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // getters and setters

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
