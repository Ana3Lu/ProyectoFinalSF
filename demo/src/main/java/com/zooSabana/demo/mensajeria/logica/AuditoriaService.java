package com.zooSabana.demo.mensajeria.logica;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuditoriaService {

    public void registrarEvento(Map<String, Object> mensaje) {
        // Simulación
        System.out.println("Registrando evento en el log de auditoría: " + mensaje + "\n");
    }
}
