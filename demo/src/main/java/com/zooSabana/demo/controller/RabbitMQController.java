package com.zooSabana.demo.controller;

import com.zooSabana.demo.mensajeria.logica.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    private final PublicacionService publicacionService;

    public RabbitMQController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @PostMapping("/publicar")
    public ResponseEntity<String> publicarAnimalesSinRevision() {
        publicacionService.manejarPublicacion();
        return ResponseEntity.ok("Mensajes publicados en RabbitMQ");
    }
}
