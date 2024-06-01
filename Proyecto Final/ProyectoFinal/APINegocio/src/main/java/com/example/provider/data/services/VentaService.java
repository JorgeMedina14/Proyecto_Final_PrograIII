package com.example.provider.data.services;

import com.example.provider.entities.Venta;

import java.util.Date;
import java.util.List;

public interface VentaService {
    public void saveVenta(Venta venta);
    public List<Venta> getAllVentas();
    public List<Venta> getAllVentasByFechaBetween(Date inicio, Date fin);
    public List<Venta> getAllVentasByProducto(Long id);
    public List<Venta> getAllVentasByCliente(Long id);
    public List<Venta> getAllVentasByProveedor(Long id);
    public Venta getVentaById(Long id);
    public void deleteVentaById(Long id);
    public void updateVenta(Long id, Venta venta);
    public void updateEstadoVenta(Long id, String estado);
}
