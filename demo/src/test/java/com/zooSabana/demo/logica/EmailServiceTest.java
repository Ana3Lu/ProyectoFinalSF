package com.zooSabana.demo.logica;

import com.zooSabana.demo.mensajeria.logica.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
    }

    @Test
    void shouldSendEmailSuccessfully() {
        String to = "test@example.com";
        String subject = "Prueba de correo";
        String body = "Este es el contenido del correo de prueba.";

        emailService.enviarEmail(to, subject, body);

        System.out.println("Correo enviado exitosamente a " + to);
    }

    @Test
    void shouldHandleEmptyEmailFields() {
        String to = "";
        String subject = "";
        String body = "";

        emailService.enviarEmail(to, subject, body);

        System.out.println("Prueba completada con campos vacíos.");
    }

    @Test
    void shouldHandleNullEmailFields() {
        String to = null;
        String subject = null;
        String body = null;

        emailService.enviarEmail(to, subject, body);

        System.out.println("Prueba completada con valores nulos.");
    }
}

