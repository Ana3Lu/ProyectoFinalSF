package com.zooSabana.demo.mensajeria.consumidores;

import com.zooSabana.demo.logica.AuditoriaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumidorAuditoria {

    private final AuditoriaService auditoriaService;

    public ConsumidorAuditoria(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @RabbitListener(queues = "eventos_auditoria")
    public void registrarAuditoria(Map<String, Object> mensaje) {
        System.out.println("Evento recibido en auditoría: " + mensaje);

        auditoriaService.registrarEvento(mensaje);
    }
}


