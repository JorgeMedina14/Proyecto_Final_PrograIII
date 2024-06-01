package com.example.consumer.data.controllers;

import com.example.consumer.dao.ProductosDao;
import com.example.consumer.entities.Producto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class Productos {

    @Autowired
    private ProductosDao productosDao;

    @GetMapping
    public Page<Producto> getProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoria) {

        Specification<Producto> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (categoria != null) {
                predicates.add(cb.equal(root.get("Categoría"), categoria));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productosDao.findAll(spec, PageRequest.of(page, size));
    }
}
