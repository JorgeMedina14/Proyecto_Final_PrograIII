package com.example.provider.dao;

import com.example.provider.entities.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VentaDao extends CrudRepository<Venta, Long> {
    public List<Venta> findAllByFechaVentaBetween(Date inicio, Date fin);
    @Query("SELECT v FROM Venta v CROSS JOIN VentaProducto vp WHERE vp.producto.id = :productoId")
    public List<Venta> findAllByProducto(@Param("productoId") Long id);
    public List<Venta> findAllByCliente(Long id);
    @Query("SELECT v FROM Venta v CROSS JOIN VentaProducto vp CROSS JOIN Producto p WHERE p.proveedor.id = :proveedorId")
    public List<Venta> findAllByProveedor(@Param("proveedorId") Long id);
}
