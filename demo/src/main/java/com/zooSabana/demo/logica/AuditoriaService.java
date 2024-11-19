package com.zooSabana.demo.logica;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuditoriaService {

    public void registrarEvento(Map<String, Object> mensaje) {
        // Simulación
        System.out.println("Registrando evento en auditoría: " + mensaje);
    }
}
