package com.example.provider.data.controllers;

import com.example.provider.data.services.ReporteService;
import com.example.provider.entities.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/tiempo/{inicio}/{fin}")
    public Reporte reportePorTiempo(@PathVariable Long inicio, @PathVariable Long fin) {
        Date inicioDate = new Date(inicio);
        Date finDate = new Date(fin);
        return reporteService.generarReportePorTiempo(inicioDate, finDate);
    }

    @GetMapping("/producto/{id}")
    public Reporte reportePorProducto(@PathVariable Long id) {
        return reporteService.generarReportePorProducto(id);
    }

    @GetMapping("/cliente/{id}")
    public Reporte reportePorCliente(@PathVariable Long id) {
        return reporteService.generarReportePorCliente(id);
    }

    @GetMapping("/proveedor/{id}")
    public Reporte reportePorProveedor(@PathVariable Long id) {
        return reporteService.generarReportePorProveedor(id);
    }
}
