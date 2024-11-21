package com.zooSabana.demo.logica;

import com.zooSabana.demo.mensajeria.consumidores.ConsumidorNotificacion;
import com.zooSabana.demo.mensajeria.logica.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class ConsumidorNotificacionesTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ConsumidorNotificacion consumidorNotificacion;

    @Test
    void procesarNotificacion_validMessage_shouldSendEmail() {
        Map<String, Object> mensaje = Map.of(
                "ultimaFechaRevision", List.of(2023, 10, 15),
                "nombre", "Tigre",
                "cuidador", "Juan Perez",
                "especie", "Felino",
                "emailCuidador", "juan.perez@example.com"
        );

        consumidorNotificacion.procesarNotificacion(mensaje);

        Mockito.verify(emailService).enviarEmail(
                eq("juan.perez@example.com"),
                eq("Revisión pendiente para Tigre"),
                contains("El animal Tigre (Especie: Felino)")
        );
    }
}
