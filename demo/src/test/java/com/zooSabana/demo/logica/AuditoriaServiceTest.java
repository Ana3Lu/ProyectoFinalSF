package com.zooSabana.demo.logica;

import com.zooSabana.demo.mensajeria.logica.AuditoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class AuditoriaServiceTest {

    private AuditoriaService auditoriaService;

    @BeforeEach
    void setUp() {
        auditoriaService = new AuditoriaService();
    }

    @Test
    void shouldRegisterEventoSuccessfully() {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "CREAR");
        mensaje.put("usuario", "admin");
        mensaje.put("timestamp", "2024-11-21T08:56:30");

        auditoriaService.registrarEvento(mensaje);

        System.out.println("Prueba completada exitosamente.");
    }

    @Test
    void shouldHandleEmptyEvento() {
        Map<String, Object> mensaje = new HashMap<>();

        auditoriaService.registrarEvento(mensaje);

        System.out.println("Prueba completada para evento vacío.");
    }
}

