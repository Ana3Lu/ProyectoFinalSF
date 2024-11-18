package com.zooSabana.demo.mensajeria.consumidores;

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

