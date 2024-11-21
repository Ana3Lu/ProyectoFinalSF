package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.AnimalDTO;
import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.db.jpa.AnimalJPA;
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

import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class AnimalControllerIntegrationTest {

    AnimalDTO animalDTO;

    @Autowired
    AnimalJPA animalJPA;

    @Autowired
    EspecieJPA especieJPA;

    @Autowired
    CuidadorJPA cuidadorJPA;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setNombre("Juan Perez");
        cuidador.setEmail("juan.perez@example.com");
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setNombre("Canguro");
        especie.setCuidador(cuidador);
        especie = especieJPA.save(especie);

        animalDTO = new AnimalDTO(especie.getId(), "Canguro", 3);
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
    void shouldNotCreateAnimalWithInvalidName() {
        animalDTO = new AnimalDTO(1L, "", 3);
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/animal", animalDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Nombre de animal inválido", respuesta.getBody());
    }

    @Test
    void shouldNotCreateAnimalWithInvalidEdad() {
        animalDTO = new AnimalDTO(1L, "Canguro", -3);
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/animal", animalDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Edad de animal inválida", respuesta.getBody());
    }

    @Test
    void shouldGetAllAnimalesSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/animales", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(ArrayList.class, Objects.requireNonNull(respuesta.getBody()).getClass());
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
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/animales-especie?especie_id=" + id, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de especie inválido", respuesta.getBody());
    }

    @Test
    void shouldGetAnimalByIdSuccessfully() {
        AnimalORM animal = new AnimalORM();
        animal = animalJPA.save(animal);

        ResponseEntity<AnimalORM> respuesta = testRestTemplate.getForEntity("/animales/" + animal.getId(), AnimalORM.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetAnimalByIdWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/animales/" + id, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
    }

    @Test
    void shouldNotGetAnimalByIdWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/animales/" + id, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
    }

    @Test
    void shouldUpdateAnimalSuccessfully() {
        EspecieORM especie = new EspecieORM();
        especie = especieJPA.save(especie);

        AnimalORM animal = new AnimalORM();
        animal.setEspecie(especie);
        animal = animalJPA.save(animal);

        long id = animal.getId();

        AnimalDTO newAnimalDTO = new AnimalDTO(1L, "Oso", 7);

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.PUT,
                new HttpEntity<>(newAnimalDTO), String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Animal actualizado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateAnimalWithNonExistentId() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie = especieJPA.save(especie);

        AnimalDTO animalDTO = new AnimalDTO(especie.getId(), "Perro", 5);

        ResponseEntity<String> response = testRestTemplate.exchange("/animales/900", HttpMethod.PUT,
                new HttpEntity<>(animalDTO), String.class
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Animal no encontrado", response.getBody());
    }

    @Test
    void shouldNotUpdateAnimalWithInvalidId() {
        AnimalDTO animalDTO = new AnimalDTO(1L, "Perro", 5);

        ResponseEntity<String> response = testRestTemplate.exchange("/animales/-5", HttpMethod.PUT,
                    new HttpEntity<>(animalDTO), String.class
            );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("ID de animal inválido", response.getBody());
    }

    @Test
    void shouldDeleteAnimalSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setNombre("Juan Pérez");
        cuidador.setEmail("juan.perez@zooUser.com");
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie.setNombre("Perro");
        especie = especieJPA.save(especie);

        AnimalORM nuevoAnimal = new AnimalORM();
        nuevoAnimal.setEspecie(especie);
        animalJPA.save(nuevoAnimal);

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + nuevoAnimal.getId(), HttpMethod.DELETE, null, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Animal eliminado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteAnimalWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteAnimalWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/animales/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
    }
}
