package com.example.consumer.dao;

import com.example.consumer.entities.Producto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductosDao extends PagingAndSortingRepository<Producto,Long>, JpaSpecificationExecutor<Producto> {
}
