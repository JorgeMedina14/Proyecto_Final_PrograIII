package com.example.provider.data.services;

import com.example.provider.dao.ReporteDao;
import com.example.provider.entities.Producto;
import com.example.provider.entities.Reporte;
import com.example.provider.entities.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteDao reporteDao;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;


    @Override
    public Reporte generarReportePorTiempo(Date inicio, Date fin) {
        Reporte reporte = new Reporte();
        List<Venta> ventas = ventaService.getAllVentasByFechaBetween(inicio, fin);
        ventas.removeIf(venta -> !venta.getReportes().isEmpty());
        reporte.setVentas(ventas);
        var totalVentas = generarTotalVentas(ventaService.getAllVentasByFechaBetween(inicio, fin));
        reporte.setTotalVentas(totalVentas);
        reporte.setFechaGeneracion(new Date());
        reporteDao.save(reporte);
        return reporte;
    }

    @Override
    public Reporte generarReportePorProducto(Long id) {
        var reporte = new Reporte();
        reporte.setVentas(ventaService.getAllVentasByProducto(id));
        System.out.println(ventaService.getAllVentasByProducto(id));
        var totalVentas = generarTotalVentas(ventaService.getAllVentasByProducto(id));
        reporte.setTotalVentas(totalVentas);
        reporte.setFechaGeneracion(new Date());
        reporteDao.save(reporte);
        return reporte;
    }

    @Override
    public Reporte generarReportePorCliente(Long id) {
        var reporte = new Reporte();
        reporte.setVentas(ventaService.getAllVentasByCliente(id));
        var totalVentas = generarTotalVentas(ventaService.getAllVentasByCliente(id));
        reporte.setTotalVentas(totalVentas);
        reporte.setFechaGeneracion(new Date());
        reporteDao.save(reporte);
        return reporte;
    }

    @Override
    public Reporte generarReportePorProveedor(Long id) {
        var reporte = new Reporte();
        reporte.setVentas(ventaService.getAllVentasByProveedor(id));
        var totalVentas = generarTotalVentas(ventaService.getAllVentasByProveedor(id));
        reporte.setTotalVentas(totalVentas);
        reporte.setFechaGeneracion(new Date());
        reporteDao.save(reporte);
        return reporte;
    }

    public Double generarTotalVentas(List<Venta> ventas) {
        return ventas
                .stream()
                .map(venta -> venta.getVentaProductos()
                        .stream()
                        .map(ventaProducto -> {
                            Producto producto = productoService.findById(ventaProducto.getProducto().getId());
                            return producto.getPrecio() * ventaProducto.getCantidad();
                        })
                        .reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum);
    }
}
