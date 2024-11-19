package com.zooSabana.demo.logica;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(String to, String subject, String body) {
        System.out.println("Enviando correo a: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo:\n" + body);
    }
}
