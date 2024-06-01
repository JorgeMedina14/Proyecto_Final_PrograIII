package com.example.provider.data.services;

import com.example.provider.dao.VentaDao;
import com.example.provider.entities.Venta;
import com.example.provider.entities.VentaProducto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void saveVenta(Venta venta) {
        // Comprobar que los productos de la venta existen en la base de datos y que hay suficientes
        for (VentaProducto vp : venta.getVentaProductos()) {
            if (productoService.findById(vp.getProducto().getId()) == null) {
                throw new RuntimeException("Producto no encontrado");
            }
            if (productoService.findById(vp.getProducto().getId()).getStock() < vp.getCantidad()) {
                throw new RuntimeException("No hay suficiente stock");
            }
        }
        // Actualizar el stock de los productos
        for (VentaProducto ventaProducto : venta.getVentaProductos()) {
            var producto = productoService.findById(ventaProducto.getProducto().getId());
            producto.setStock(producto.getStock() - ventaProducto.getCantidad());
            productoService.save(producto);
        }
        ventaDao.save(venta);

        try {
            String ventaJson = objectMapper.writeValueAsString(venta);
            System.out.println(ventaJson);
            kafkaTemplate.send("pedidos", ventaJson);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el pedido");
        }
    }

    @Override
    public List<Venta> getAllVentas() {
        return (List<Venta>) ventaDao.findAll();
    }

    @Override
    public List<Venta> getAllVentasByFechaBetween(Date inicio, Date fin) {
        return ventaDao.findAllByFechaVentaBetween(inicio, fin);
    }

    @Override
    public List<Venta> getAllVentasByProducto(Long id) {
        return ventaDao.findAllByProducto(id);
    }

    @Override
    public List<Venta> getAllVentasByCliente(Long id) {
        return ventaDao.findAllByCliente(id);
    }

    @Override
    public List<Venta> getAllVentasByProveedor(Long id) {
        return ventaDao.findAllByProveedor(id);
    }

    @Override
    public Venta getVentaById(Long id) {
        return ventaDao.findById(id).orElse(null);
    }

    @Override
    public void deleteVentaById(Long id) {
        if (ventaDao.existsById(id)) {
            ventaDao.deleteById(id);
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }

    @Override
    public void updateVenta(Long id, Venta venta) {
        if (ventaDao.existsById(id)) {
            venta.setId(id);
            ventaDao.save(venta);
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }

    @Override
    public void updateEstadoVenta(Long id, String estado) {
        if (ventaDao.existsById(id)) {
            Venta venta = ventaDao.findById(id).orElse(null);
            assert venta != null;
            venta.setEstado(estado);
            ventaDao.save(venta);
            try {
                String ventaJson = objectMapper.writeValueAsString(venta);
                kafkaTemplate.send("pedidos", ventaJson);
            } catch (Exception e) {
                throw new RuntimeException("Error al enviar el pedido");
            }
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }
}
