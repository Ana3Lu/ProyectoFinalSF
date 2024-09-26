package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.AnimalDTO;
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

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class AnimalControllerIntegrationTest {

    private AnimalDTO animalDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
       animalDTO = new AnimalDTO(1L, "Canguro", 3);
    }

    @Test
    void shouldCreateAnimalSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/animal", animalDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Animal guardado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateAnimalWithInvalidEspecieId() {
        animalDTO = new AnimalDTO(-1L, "Canguro", 3);
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/animal", animalDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldGetAllAnimalesSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(List.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

    @Test
    void shouldGetAnimalByEspecieIdSuccessfully() {
        long id = 1;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales-especie?especie_id=" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetAnimalByEspecieIdWithInvalidEspecieId() {
        long id = 900;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales-especie?especie_id=" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldGetAnimalByIdSuccessfully() {
        long id = 1;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales/" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetAnimalByIdWithInvalidId() {
        long id = 900;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales/" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldUpdateAnimalSuccessfully() {
        long id = 1;
        AnimalDTO newAnimalDTO = new AnimalDTO(1L, "Oso", 7);
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.PUT,
                new HttpEntity<>(newAnimalDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Animal actualizado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateAnimalWithInvalidId() {
        long id = -5;
        AnimalDTO newAnimalDTO = new AnimalDTO(1L, "Oso", 7);
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.PUT,
                new HttpEntity<>(newAnimalDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
        }
    }

    @Test
    void shouldDeleteAnimalSuccessfully() {
        long id = 1;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Animal eliminado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteAnimalWithInvalidId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        if (respuesta.getStatusCode() == HttpStatus.NOT_FOUND) {
            Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
        } else {
            Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
        }
    }
}
