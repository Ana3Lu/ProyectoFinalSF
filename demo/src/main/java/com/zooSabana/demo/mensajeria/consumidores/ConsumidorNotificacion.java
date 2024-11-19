package com.zooSabana.demo.mensajeria.consumidores;

import com.zooSabana.demo.logica.EmailService;
import com.zooSabana.demo.logica.NotificacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ConsumidorNotificacion {

    private final EmailService emailService;

    public ConsumidorNotificacion(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "revisiones_pendientes")
    public void procesarNotificacion(Map<String, Object> mensaje) {
        System.out.println("Notificación recibida: " + mensaje);

        String email = (String) mensaje.get("emailCuidador");
        String nombreCuidador = (String) mensaje.get("cuidador");
        String nombreAnimal = (String) mensaje.get("nombre");
        String especie = (String) mensaje.get("especie");
        LocalDate ultimaRevision = (LocalDate) mensaje.get("ultimaFechaRevision");

        String asunto = "Revisión pendiente para " + nombreAnimal;
        String cuerpo = "Hola " + nombreCuidador + ",\n\n"
                + "El animal " + nombreAnimal + " (Especie: " + especie + ") "
                + "tiene pendiente una revisión mensual desde la fecha: " + ultimaRevision + ".\n\n"
                + "Por favor, realiza la revisión lo antes posible.\n\n"
                + "Saludos,\nZoo Sabana";

        emailService.enviarEmail(email, asunto, cuerpo);
    }
}



