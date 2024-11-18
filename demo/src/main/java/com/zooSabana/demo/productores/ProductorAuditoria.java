package com.zooSabana.demo.productores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooSabana.demo.logica.RegistroMedicoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductorAuditoria {

    private final RegistroMedicoService registroMedicoService;
    private final RabbitTemplate rabbitTemplate;

    public ProductorAuditoria(RegistroMedicoService registroMedicoService, RabbitTemplate rabbitTemplate) {
        this.registroMedicoService = registroMedicoService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarAnimalesSinRevision() {
        List<Map<String, Object>> animalesSinRevision = registroMedicoService.getAnimalesSinRevision();

        if (animalesSinRevision.isEmpty()) {
            return;
        }

        for (Map<String, Object> animal : animalesSinRevision) {
            rabbitTemplate.convertAndSend("direct_exchange", "notificaciones", animal); // El RabbitTemplate debe manejar JSON
            rabbitTemplate.convertAndSend("direct_exchange", "auditoria", animal);
        }
    }
}

