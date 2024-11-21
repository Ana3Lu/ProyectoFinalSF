package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.RabbitMQController;
import com.zooSabana.demo.mensajeria.logica.PublicacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RabbitMQController.class)
public class RabbitMQControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionService publicacionService;

    @Test
    void publicarAnimalesSinRevisionDeberiaResponderExitosamente() throws Exception {
        doNothing().when(publicacionService).manejarPublicacion();

        mockMvc.perform(post("/rabbitmq/alerta-revision-pendiente-mes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Mensajes publicados en RabbitMQ"));

        Mockito.verify(publicacionService, Mockito.times(1)).manejarPublicacion();
    }

}
