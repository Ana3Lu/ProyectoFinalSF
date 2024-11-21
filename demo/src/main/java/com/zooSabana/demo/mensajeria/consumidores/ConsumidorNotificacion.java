package com.zooSabana.demo.mensajeria.consumidores;

import com.zooSabana.demo.mensajeria.logica.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ConsumidorNotificacion {

    private final EmailService emailService;

    public ConsumidorNotificacion(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "revisiones_pendientes")
        public void procesarNotificacion(Map<String, Object> mensaje) {
        try {
            Object fechaRaw = mensaje.get("ultimaFechaRevision");
            LocalDate ultimaFechaRevision = null;

            if (fechaRaw instanceof List) {
                List<Integer> fechaList = (List<Integer>) fechaRaw;
                ultimaFechaRevision = LocalDate.of(fechaList.get(0), fechaList.get(1), fechaList.get(2));
            }

            String nombre = (String) mensaje.get("nombre");
            String cuidador = (String) mensaje.get("cuidador");
            String especie = (String) mensaje.get("especie");
            String emailCuidador = (String) mensaje.get("emailCuidador");

            System.out.printf("Notificación recibida de animal pendiente de revisión médica en el mes. Detalles: %s (%s). Cuidador: %s, Email: %s, Última Revisión del animal: %s%n",
                    nombre, especie, cuidador, emailCuidador, ultimaFechaRevision);

            String asunto = "Revisión pendiente para " + nombre;
            String cuerpo = "Hola " + cuidador + ",\n\n"
                    + "El animal " + nombre + " (Especie: " + especie + ") "
                    + "tiene pendiente la revisión del mes actual.\n\n"
                    + "Por favor, realizarla lo más pronto posible.\n\n"
                    + "Saludos,\nZoo Sabana\n\n";

            emailService.enviarEmail(emailCuidador, asunto, cuerpo);

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la notificación", e);
        }
    }

}



