package com.zooSabana.demo.productores;

import com.zooSabana.demo.logica.RegistroMedicoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductorAuditoria {

    private RegistroMedicoService registroMedicoService;

    private RabbitTemplate rabbitTemplate;

    @Autowired
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
            rabbitTemplate.convertAndSend("direct_exchange", "notificaciones", animal);
            rabbitTemplate.convertAndSend("direct_exchange", "auditoria", animal);
        }
    }
}
