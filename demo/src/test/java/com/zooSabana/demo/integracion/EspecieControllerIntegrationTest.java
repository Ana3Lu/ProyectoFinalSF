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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class EspecieControllerIntegrationTest {

    private EspecieDTO especieDTO;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        especieDTO = new EspecieDTO(null,"Ave");
    }

    @Test
    void givenValidEspecieDTO_whenCreateEspecie_thenReturnCreated() {
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        Assertions.assertEquals("Especie guardada exitosamente", respuesta.getBody());
    }

    @Test
    void givenInvalidEspecieDTO_whenCreateEspecie_thenReturnNotFound() {
        especieDTO = new EspecieDTO(900L, "");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Especie no encontrada", response.getBody());
    }

    @Test
    void givenInvalidEspecieDTO_whenCreateEspecie_thenReturnBadRequest() {
        especieDTO = new EspecieDTO(-5L, "");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/especie", especieDTO, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Nombre de especie inválido", response.getBody());
    }

    @Test
    void whenGetEspecies_thenReturnListOfEspecies() {
        ResponseEntity<Object> response = testRestTemplate.getForEntity("/especies", Object.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void givenValidId_whenGetEspecieById_thenReturnEspecie() {
        Long id = especieDTO.id();
        ResponseEntity<Object> response = testRestTemplate.getForEntity("/especies/" + id, Object.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void givenInvalidId_whenGetEspecieById_thenReturnNotFound() {
        Long id = 900L;
        ResponseEntity<String> response = testRestTemplate.getForEntity("/especies/" + id, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Especie no encontrada", response.getBody());
    }

    @Test
    void givenInvalidId_whenGetEspecieById_thenReturnBadRequest() {
        Long id = -5L;
        ResponseEntity<String> response = testRestTemplate.getForEntity("/especies/" + id, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Id de especie inválido", response.getBody());
    }

    @Test
    void givenValidIdAndNombre_whenUpdateEspecie_thenReturnUpdated() {
        Long id = especieDTO.id();
        String newNombre = "Reptil";
        ResponseEntity<String> response = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newNombre), String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Especie actualizada exitosamente", response.getBody());
    }

    @Test
    void givenInvalidId_whenUpdateEspecie_thenReturnNotFound() {
        Long id = 900L;
        String newNombre = "Reptil";
        ResponseEntity<String> response = testRestTemplate.exchange("/especies/" + id, HttpMethod.PUT,
                new HttpEntity<>(newNombre), String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Especie no encontrada", response.getBody());
    }

    @Test
    void givenValidId_whenDeleteEspecie_thenReturnDeleted() {
        Long id = especieDTO.id();
        ResponseEntity<String> response = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Especie eliminada exitosamente", response.getBody());
    }

    @Test
    void givenInvalidId_whenDeleteEspecie_thenReturnNotFound() {
        Long id = 900L;
        ResponseEntity<String> response = testRestTemplate.exchange("/especies/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Especie no encontrada", response.getBody());
    }
}
