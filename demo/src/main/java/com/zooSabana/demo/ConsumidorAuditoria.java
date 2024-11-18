package com.zooSabana.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumidorAuditoria {

    @RabbitListener(queues = "eventos_auditoria")
    public void registrarAuditoria(Map<String, Object> mensaje) {
        System.out.println("Evento recibido en auditoría: " + mensaje);
    }
}

