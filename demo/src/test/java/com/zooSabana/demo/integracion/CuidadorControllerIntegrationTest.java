package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.CuidadorDTO;
import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.orm.CuidadorORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class CuidadorControllerIntegrationTest {

    CuidadorDTO cuidadorDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    CuidadorJPA cuidadorJPA;

    @BeforeEach
    void setUp() {
        cuidadorDTO = new CuidadorDTO("Miguel Lopez", "migelLp@hotmail.com");
    }

    @Test
    void shouldCreateCuidadorSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/cuidador", cuidadorDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Cuidador guardado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateCuidadorWithInvalidName() {
        cuidadorDTO = new CuidadorDTO("", "migelLp@gmailcom");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/cuidador", cuidadorDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Nombre de cuidador inválido", respuesta.getBody());
    }

    @Test
    void shouldGetAllCuidadoresSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/cuidadores", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(ArrayList.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

    @Test
    void shouldGetCuidadorByIdSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador = cuidadorJPA.save(cuidador);
        ResponseEntity<CuidadorORM> respuesta = testRestTemplate.getForEntity("/cuidadores/" + cuidador.getId(), CuidadorORM.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetCuidadorByIdWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/cuidadores/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Cuidador no encontrado", respuesta.getBody());
    }

    @Test
    void shouldNotGetCuidadorByIdWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/cuidadores/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de cuidador inválido", respuesta.getBody());
    }

    @Test
    void shouldUpdateCuidadorSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador = cuidadorJPA.save(cuidador);
        long id = cuidador.getId();
        CuidadorDTO newCuidadorDTO = new CuidadorDTO("Andrea Perez", "andreaPp@gmailcom");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.PUT,
                new HttpEntity<>(newCuidadorDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Cuidador actualizado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateCuidadorWithInvalidId() {
        long id = -5;
        CuidadorDTO newCuidadorDTO = new CuidadorDTO("Andrea Perez", "andreaPp@gmailcom");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.PUT,
                new HttpEntity<>(newCuidadorDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de cuidador inválido", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateCuidadorWithNonExistentId() {
        long id = 900;
        CuidadorDTO newCuidadorDTO = new CuidadorDTO("Andrea Perez", "andreaPp@gmailcom");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.PUT,
                new HttpEntity<>(newCuidadorDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Cuidador no encontrado", respuesta.getBody());
    }

    @Test
    void shouldDeleteCuidadorSuccessfully() {
        CuidadorORM nuevoCuidador = new CuidadorORM();
        nuevoCuidador.setNombre("Miguel Lopez");
        nuevoCuidador = cuidadorJPA.save(nuevoCuidador);

        long id = nuevoCuidador.getId();

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.DELETE, null, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Cuidador eliminado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteCuidadorWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Cuidador no encontrado", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteCuidadorWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/cuidadores/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de cuidador inválido", respuesta.getBody());
    }
}
