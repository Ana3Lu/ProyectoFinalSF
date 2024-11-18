package com.zooSabana.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumidorNotificacion {

    @RabbitListener(queues = "revisiones_pendientes")
    public void procesarNotificacion(Map<String, Object> mensaje) {
        System.out.println("Notificación recibida: " + mensaje);
    }
}

