package com.example.interfaznegocio.models;

public class CompraProducto {
    private Long id;
    private String producto;
    private String proveedor;
    private Integer cantidad;

    public CompraProducto() {
    }

    public CompraProducto(Long id, String producto, String proveedor, Integer cantidad) {
        this.id = id;
        this.producto = producto;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
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
}
