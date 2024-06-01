package com.example.consumer.dao;

import com.example.consumer.entities.CarritoDeCompras;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CarritoDeComprasDao extends CrudRepository<CarritoDeCompras, Long> {
    @Query("SELECT c FROM CarritoDeCompras c WHERE c.cliente.id = :id")
    public CarritoDeCompras findCarritoDeComprasByCliente_Id(@Param("id") Long id);
}
