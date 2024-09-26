package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.EspecieDTO;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class EspecieControllerIntegrationTest {

    private EspecieDTO especieDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        especieDTO = new EspecieDTO("Ave");
    }

    @Test
    void shouldCreateAnimalSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie guardada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateAnimalWithInvalidName() {
        especieDTO = new EspecieDTO("");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Nombre de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldGetAllEspeciesSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/especies", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(List.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

    @Test
    void shouldGetEspecieByIdSuccessfully() {
        long id = 1;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/especies/" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetEspecieByIdWithInvalidId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/especies/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldUpdateEspecieSuccessfully() {
        long id = 1;
        EspecieDTO newEspecieDTO = new EspecieDTO("Reptil");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie actualizada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateEspecieWithInvalidId() {
        long id = -5;
        EspecieDTO newEspecieDTO = new EspecieDTO("Reptil");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldDeleteEspecieSuccessfully() {
        long id = 1;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie eliminada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteEspecieWithInvalidId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
        }
    }
}
