package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.EspecieORM;
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

import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class EspecieControllerIntegrationTest {

    EspecieDTO especieDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    EspecieJPA especieJPA;

    @BeforeEach
    void setUp() {
        especieDTO = new EspecieDTO("Ave");
    }

    @Test
    void shouldCreateEspecieSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie guardada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateEspecieWithInvalidName() {
        especieDTO = new EspecieDTO("");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Nombre de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldGetAllEspeciesSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/especies", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(ArrayList.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

    @Test
    void shouldGetEspecieByIdSuccessfully() {
        EspecieORM especie = new EspecieORM();
        especie = especieJPA.save(especie);
        ResponseEntity<EspecieORM> respuesta = testRestTemplate.getForEntity("/especies/" + especie.getId(), EspecieORM.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetEspecieByIdWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/especies/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
    }

    @Test
    void shouldNotGetEspecieByIdWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/especies/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldUpdateEspecieSuccessfully() {
        EspecieORM especie = new EspecieORM();
        especie = especieJPA.save(especie);
        long id = especie.getId();
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
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateEspecieWithNonExistentId() {
        long id = 900;
        EspecieDTO newEspecieDTO = new EspecieDTO("Reptil");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
    }

    @Test
    void shouldDeleteEspecieSuccessfully() {
        EspecieORM nuevaEspecie = new EspecieORM();
        nuevaEspecie.setNombre("ave");
        nuevaEspecie = especieJPA.save(nuevaEspecie);

        long id = nuevaEspecie.getId();

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie eliminada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteEspecieWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
    }


    @Test
    void shouldNotDeleteEspecieWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }
}
