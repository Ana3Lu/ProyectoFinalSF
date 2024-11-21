package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.CuidadorDTO;
import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.CuidadorORM;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    CuidadorJPA cuidadorJPA;

    @BeforeEach
    void setUp() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setNombre("Juan Perez");
        cuidador.setEmail("juan.perez@example.com");
        cuidador = cuidadorJPA.save(cuidador);

        especieDTO = new EspecieDTO(cuidador.getId(), "Mamifero");
    }

    @Test
    void shouldCreateEspecieSuccessfully() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        Assertions.assertEquals("Especie guardada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateEspecieWithInvalidCuidadorId() {
        especieDTO = new EspecieDTO(-1L, "Mamifero");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de cuidador inválido", respuesta.getBody());
    }

    @Test
    void shouldNotCreateEspecieWithInvalidName() {
        especieDTO = new EspecieDTO(1L, "");
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
    void shouldGetEspecieByCuidadorIdSuccessfully() {
        long id = 1;
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/especies-cuidador?cuidador_id=" + id, Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetEspecieByCuidadorIdWithInvalidCuidadorId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/especies-cuidador?cuidador_id=" + id, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de cuidador inválido", respuesta.getBody());
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
        CuidadorORM cuidador = new CuidadorORM();
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie = especieJPA.save(especie);

        long id = especie.getId();

        EspecieDTO newEspecieDTO = new EspecieDTO(1L, "Mamifero");

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Especie actualizada exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateEspecieWithInvalidId() {
        long id = -5;
        EspecieDTO newEspecieDTO = new EspecieDTO(1L, "Mamifero");

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateEspecieWithNonExistentId() {
        long id = 900;

        CuidadorORM cuidador = new CuidadorORM();
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie = especieJPA.save(especie);

        EspecieDTO newEspecieDTO = new EspecieDTO(1L, "Mamifero");

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newEspecieDTO), String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        Assertions.assertEquals("Especie no encontrada", respuesta.getBody());
    }

    @Test
    void shouldDeleteEspecieSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setId(1L);
        cuidador.setNombre("Juan Perez");
        cuidador.setEmail("juan.perez@example.com");
        cuidadorJPA.save(cuidador);

        EspecieORM nuevaEspecie = new EspecieORM();
        nuevaEspecie.setId(1L);
        nuevaEspecie.setNombre("Perro");
        nuevaEspecie.setCuidador(cuidador);
        especieJPA.save(nuevaEspecie);

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/especies/" + nuevaEspecie.getId(), HttpMethod.DELETE, null, String.class);

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
