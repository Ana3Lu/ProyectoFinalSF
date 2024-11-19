package com.zooSabana.demo.integracion;

import com.zooSabana.demo.controller.dto.RegistroMedicoDTO;
import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.jpa.RegistroMedicoJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.CuidadorORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
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
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class RegistroMedicoControllerIntegrationTest {

    private RegistroMedicoDTO registroMedicoDTO;

    @Autowired
    RegistroMedicoJPA registroMedicoJPA;

    @Autowired
    AnimalJPA animalJPA;

    @Autowired
    EspecieJPA especieJPA;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CuidadorJPA cuidadorJPA;

    @BeforeEach
    void setUp() {
        registroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2024, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
    }

    @Test
    void shouldCreateRegistroMedicoSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setNombre("Juan Perez");
        cuidador.setEmail("juan.perez@example.com");
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie.setNombre("Mamifero");
        especie = especieJPA.save(especie);

        AnimalORM animal = new AnimalORM();
        animal.setNombre("Rex");
        animal.setEdad(5);
        animal.setEspecie(especie);
        animal = animalJPA.save(animal);

        RegistroMedicoDTO registroMedico = new RegistroMedicoDTO(animal.getId(), LocalDate.of(2024, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/registro-medico", registroMedico, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro médico guardado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotCreateRegistroMedicoWithInvalidFecha() {
        registroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2025, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/registro-medico", registroMedicoDTO, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Fecha de registro médico inválida", respuesta.getBody());
    }

    @Test
    void shouldNotCreateRegistroMedicoWithNonExistendAnimalId() {
        RegistroMedicoDTO registroMedico = new RegistroMedicoDTO(900L, LocalDate.of(2024, 7, 1), "Saludable", "Carnivoro", "Tranquilo");
        ResponseEntity<String> respuesta = testRestTemplate.postForEntity("/registro-medico", registroMedico, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
    }

    @Test
    void shouldGetAllRegistrosMedicosSuccessfully() {
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos", Object.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(ArrayList.class, Objects.requireNonNull(respuesta.getBody()).getClass());
    }

   @Test
    void shouldGetRegistroMedicoByIdByIdSuccessfully() {
        RegistroMedicoORM registroMedico = new RegistroMedicoORM();
        registroMedico = registroMedicoJPA.save(registroMedico);

        long id = registroMedico.getId();
        ResponseEntity<RegistroMedicoORM> respuesta = testRestTemplate.getForEntity("/registros-medicos/" + id, RegistroMedicoORM.class);
        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetRegistroMedicoByIdByIdWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/registros-medicos/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de registro médico inválido", respuesta.getBody());
    }

    @Test
    void shouldNotGetRegistroMedicoByIdByIdWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/registros-medicos/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Registro médico no encontrado", respuesta.getBody());
    }

    @Test
    void shouldGetRegistroMedicoByAnimalIdSuccessfully() {
        CuidadorORM cuidador = new CuidadorORM();
        cuidador.setNombre("Juan Perez");
        cuidador.setEmail("juan.perez@example.com");
        cuidador = cuidadorJPA.save(cuidador);

        EspecieORM especie = new EspecieORM();
        especie.setCuidador(cuidador);
        especie.setNombre("Mamifero");
        especie = especieJPA.save(especie);

        AnimalORM animal = new AnimalORM();
        animal.setNombre("Rex");
        animal.setEdad(5);
        animal.setEspecie(especie);
        animal = animalJPA.save(animal);

        RegistroMedicoORM registroMedico = new RegistroMedicoORM();
        registroMedico.setAnimal(animal);
        registroMedico.setFecha(LocalDate.now());
        registroMedico = registroMedicoJPA.save(registroMedico);

        long animalId = animal.getId();
        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos/animales/" + animalId, Object.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(respuesta.getBody());
    }

    @Test
    void shouldNotGetRegistroMedicoByAnimalIdWithInvalidAnimalId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/registros-medicos/animales/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de animal inválido", respuesta.getBody());
    }

    @Test
    void shouldNotGetRegistroMedicoByAnimalIdWithNonExistentAnimalId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.getForEntity("/registros-medicos/animales/" + id, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Animal no encontrado", respuesta.getBody());
    }

    @Test
    void shouldGetAnimalesSinRevisionSuccessfully() {
        EspecieORM especie = new EspecieORM();
        especie.setNombre("Gato");
        especie = especieJPA.save(especie);

        AnimalORM animal1 = new AnimalORM();
        animal1.setNombre("Miau");
        animal1.setEdad(2);
        animal1.setEspecie(especie);
        animal1 = animalJPA.save(animal1);

        AnimalORM animal2 = new AnimalORM();
        animal2.setNombre("Rex");
        animal2.setEdad(5);
        animal2.setEspecie(especie);
        animal2 = animalJPA.save(animal2);

        RegistroMedicoORM registro = new RegistroMedicoORM();
        registro.setAnimal(animal1);
        registro.setFecha(LocalDate.now());
        registroMedicoJPA.save(registro);

        ResponseEntity<Object> respuesta = testRestTemplate.getForEntity("/registros-medicos/animales/revision-pendiente-mes", Object.class);

        Assertions.assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Assertions.assertNotNull(respuesta.getBody());
    }

    /*@Test
    void shouldUpdateRegistroMedicoSuccessfully() {
        EspecieORM especie = new EspecieORM();
        especie = especieJPA.save(especie);

        AnimalORM animal = new AnimalORM();
        animal.setEspecie(especie);
        animal = animalJPA.save(animal);

        RegistroMedicoORM registroMedico = new RegistroMedicoORM();
        registroMedico.setAnimal(animal);
        registroMedico = registroMedicoJPA.save(registroMedico);

        long id = registroMedico.getId();

        RegistroMedicoDTO newRegistroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2023,8,3), "Estable", "Mixta", "Tranquilo");

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registro-medico/" + id, HttpMethod.PUT,
                new HttpEntity<>(newRegistroMedicoDTO), String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro médico actualizado exitosamente", respuesta.getBody());
    }*/

    @Test
    void shouldNotUpdateRegistroMedicoWithInvalidId() {
        long id = -5;
        RegistroMedicoDTO newRegistroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2023, 9, 26), "Enfermo", "Carnívora", "Agresivo");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registros-medicos/" + id, HttpMethod.PUT,
                new HttpEntity<>(newRegistroMedicoDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de registro médico inválido", respuesta.getBody());
    }

    @Test
    void shouldNotUpdateRegistroMedicoWithNonExistentId() {
        long id = 900;
        RegistroMedicoDTO newRegistroMedicoDTO = new RegistroMedicoDTO(1L, LocalDate.of(2023, 9, 26), "Enfermo", "Carnívora", "Agresivo");
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registros-medicos/" + id, HttpMethod.PUT,
                new HttpEntity<>(newRegistroMedicoDTO), String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Registro médico no encontrado", respuesta.getBody());
    }

    @Test
    void shouldDeleteRegistroMedicoSuccessfully() {
        long id = 1;
        RegistroMedicoORM nuevoRegistroMedico = new RegistroMedicoORM();
        nuevoRegistroMedico.setId(id);
        registroMedicoJPA.save(nuevoRegistroMedico);

        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registros-medicos/" + id, HttpMethod.DELETE, null, String.class);

        Assertions.assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Registro médico eliminado exitosamente", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteRegistroMedicoWithInvalidId() {
        long id = -5;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registros-medicos/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ID de registro médico inválido", respuesta.getBody());
    }

    @Test
    void shouldNotDeleteRegistroMedicoWithNonExistentId() {
        long id = 900;
        ResponseEntity<String> respuesta = testRestTemplate.exchange("/registros-medicos/" + id, HttpMethod.DELETE, null, String.class);
        Assertions.assertTrue(respuesta.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Registro médico no encontrado", respuesta.getBody());
    }
}
