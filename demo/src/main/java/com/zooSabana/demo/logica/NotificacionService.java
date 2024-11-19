package com.zooSabana.demo.logica;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificacionService {

    public void enviarNotificacion(String mensaje) {
        // Aquí podrías integrar un servicio de correo o SMS
        System.out.println("Enviando notificación: " + mensaje);
    }
}
