package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.controller.dto.RegistroMedicoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.List;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class RegistroMedicoControllerIntegrationTest {

    private RegistroMedicoDTO registroMedicoDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        registroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2024, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
    }

    @Test
    void shouldCreateRegistroMedicoSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/registro-medico", registroMedicoDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro medico guardado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateRegistroMedicoWithInvalidFecha() {
        registroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2025, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/registro-medico", registroMedicoDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Fecha de registro médico inválida", respuesta.getBody());
    }

    @Test
    void shouldGetAllRegistrosMedicosSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(List.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

    @Test
    void shouldGetRegistroMedicoByIdByIdSuccessfully() {
        long id = 1;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos/" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetRegistroMedicoByIdByIdWithInvalidId() {
        long id = 900;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos/" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Registro médico no encontrado", respuesta.getBody());
    }

    @Test
    void shouldUpdateRegistroMedicoSuccessfully() {
        long id = 1;
        RegistroMedicoDTO newRegistroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2023, 9, 26), "Enfermo", "Carnívora", "Agresivo");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registro-medico/" + id, HttpMethod.PUT,
                new HttpEntity<>(newRegistroMedicoDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro médico actualizado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateRegistroMedicoWithInvalidId() {
        long id = -5;
        RegistroMedicoDTO newRegistroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2023, 9, 26), "Enfermo", "Carnívora", "Agresivo");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newRegistroMedicoDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldDeleteRegistroMedicoSuccessfully() {
        long id = 1;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registro-medico/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro médico eliminado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteRegistroMedicoWithInvalidId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registro-medico/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Registro médico no encontrado", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de registro médico inválido", respuesta.getBody());
        }
    }
}
