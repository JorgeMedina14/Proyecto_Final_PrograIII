package com.example.provider.data.services;

import com.example.provider.entities.Reporte;

import java.util.Date;

public interface ReporteService {
    public Reporte generarReportePorTiempo(Date inicio, Date fin);
    public Reporte generarReportePorProducto(Long id);
    public Reporte generarReportePorCliente(Long id);
    public Reporte generarReportePorProveedor(Long id);
}
