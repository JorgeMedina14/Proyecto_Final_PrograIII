package com.example.provider.data.controllers;

import com.example.provider.data.services.VentaService;
import com.example.provider.entities.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public void addVenta(@RequestBody Venta venta) {
        ventaService.saveVenta(venta);
    }

    @GetMapping
    public List<Venta> getVenta() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public Venta getVentaById(@PathVariable("id") Long id) {
        return ventaService.getVentaById(id);
    }

    @GetMapping("/cliente/{id}")
    public List<Venta> getVentaByCliente(@PathVariable("id") Long id) {
        return ventaService.getAllVentasByCliente(id);
    }

    @GetMapping("/producto/{id}")
    public List<Venta> getVentaByProducto(@PathVariable("id") Long id) {
        return ventaService.getAllVentasByProducto(id);
    }

    @GetMapping("/proveedor/{id}")
    public List<Venta> getVentaByProveedor(@PathVariable("id") Long id) {
        return ventaService.getAllVentasByProveedor(id);
    }

    @GetMapping("/fecha/{fecha_inicio}/{fecha_fin}")
    public List<Venta> getVentaByFecha(@PathVariable("fecha_inicio") String fecha_inicio, @PathVariable("fecha_fin") String fecha_fin) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = formato.parse(fecha_inicio);
            Date fechaFin = formato.parse(fecha_fin);
            return ventaService.getAllVentasByFechaBetween(fechaInicio, fechaFin);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}/{estado}")
    public void updateEstadoVenta(@PathVariable("id") Long id, @PathVariable("estado") String estado) {
        ventaService.updateEstadoVenta(id, estado);
    }

    @DeleteMapping("/{id}")
    public void deleteVentaById(@PathVariable("id") Long id) {
        ventaService.deleteVentaById(id);
    }
}
