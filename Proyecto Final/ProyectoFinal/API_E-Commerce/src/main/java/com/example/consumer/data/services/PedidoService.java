package com.example.consumer.data.services;

import com.example.consumer.listeners.KafkaConsumerListener;
import com.example.provider.entities.Venta;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PedidoService {

    @Autowired
    private EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @KafkaListener(topics = "pedidos", groupId = "ecommerce")
    public void processPedido(String ventaJson) {
        try {
            Venta venta = objectMapper.readValue(ventaJson, Venta.class); // Convertir JSON a Venta
            LOGGER.info("Mensaje recibido: {}", ventaJson);
            LOGGER.info("CORREO {}", venta.getEmail());
            switch (venta.getEstado()) {
                case "recibido":
                    emailService.sendSimpleEmail(venta.getEmail(), "Pedido recibido", "Su pedido ha sido recibido");
                    break;
                case "despachado":
                    emailService.sendSimpleEmail(venta.getEmail(), "Pedido despachado", "Su pedido ha sido despachado");
                    break;
                case "entregado":
                    emailService.sendSimpleEmail
                            (venta.getEmail(), "Pedido entregado", "Su pedido ha sido entregado");
                    break;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al deserializar el objeto Venta", e);
        }
    }
}
