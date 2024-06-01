package com.example.interfaznegocio.models;

import java.util.Date;
import java.util.List;

public class Reporte {
    private Long id;
    private List<Venta> ventas;
    private Double totalVentas;
    private Date fechaGeneracion;
    private Integer cantidadVentas;

    public Reporte() {
    }

    public Reporte(Long id, List<Venta> ventas, Double totalVentas, Date fechaGeneracion) {
        this.id = id;
        this.ventas = ventas;
        this.totalVentas = totalVentas;
        this.fechaGeneracion = fechaGeneracion;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public Double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Integer getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(Integer cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }
}
