package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.RegistroMedicoJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.CuidadorORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class RegistroMedicoServiceTest {

    @Mock
    RegistroMedicoJPA registroMedicoJPA;

    @Mock
    AnimalJPA animalJPA;

    @InjectMocks
    RegistroMedicoService registroMedicoService;

    @Test
    void GivenInvalidAnimalId_WhenSaveRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(-5L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidFecha_WhenSaveRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2025,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidEstado_WhenSaveRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "", "Carnivoro", "Tranquilo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), null, "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidDieta_WhenSaveRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "Saludable", "", "Tranquilo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "Saludable", null, "Tranquilo"));
    }

    @Test
    void GivenInvalidComportamiento_WhenSaveRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", ""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", null));
    }

    @Test
    void GivenNonExistentAnimalId_WhenSaveRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> registroMedicoService.saveRegistroMedico(id, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));

        Mockito.verify(animalJPA).findById(id);
    }

    @Test
    void WhenSaveRegistroMedico_ThenRegistroMedicoIsSaved() {
        long animalId = 1L;

        Mockito.when(animalJPA.findById(animalId)).thenReturn(Optional.of(new AnimalORM()));
        Mockito.when(registroMedicoJPA.save(Mockito.any(RegistroMedicoORM.class))).thenReturn(new RegistroMedicoORM());

        registroMedicoService.saveRegistroMedico(animalId, LocalDate.of(2023, 3, 1), "Saludable", "Carnivoro", "Tranquilo");

        Mockito.verify(registroMedicoJPA).save(Mockito.any(RegistroMedicoORM.class));
    }

    @Test
    void WhenGetRegistrosMedicos_ThenReturnRegistrosMedicos() {
        List<RegistroMedicoORM> registrosMedicos = new ArrayList<>();
        registrosMedicos.add(new RegistroMedicoORM());
        Mockito.when(registroMedicoJPA.findAll()).thenReturn(registrosMedicos);

        List<RegistroMedicoORM> registrosMedicosObtenidos = registroMedicoService.getRegistrosMedicos();

        Assertions.assertFalse(registrosMedicosObtenidos.isEmpty());
        Mockito.verify(registroMedicoJPA).findAll();
    }

    @Test
    void GivenInvalidId_WhenGetRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.getRegistroMedico(-5L));
    }

    @Test
    void GivenNonExistentId_WhenGetRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> registroMedicoService.getRegistroMedico(id));
        Mockito.verify(registroMedicoJPA).findById(id);
    }

    @Test
    void WhenGetRegistroMedico_ThenReturnRegistroMedico() {
        long id = 1;
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.of(new RegistroMedicoORM()));

        RegistroMedicoORM registroMedico = registroMedicoService.getRegistroMedico(id);

        Assertions.assertNotNull(registroMedico);
        Mockito.verify(registroMedicoJPA).findById(id);
    }

    @Test
    void GivenInvalidAnimalId_WhenGetRegistrosMedicosByAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.getRegistrosMedicosByAnimal(-5L));
    }

    @Test
    void WhenGetRegistrosMedicosByAnimal_ThenReturnRegistrosMedicos() {
        long id = 1;
        List<RegistroMedicoORM> registrosMedicos = new ArrayList<>();
        registrosMedicos.add(new RegistroMedicoORM());
        Mockito.when(registroMedicoJPA.findByAnimal_Id(id)).thenReturn(registrosMedicos);

        List<RegistroMedicoORM> registrosMedicosObtenidos = registroMedicoService.getRegistrosMedicosByAnimal(id);

        Assertions.assertFalse(registrosMedicosObtenidos.isEmpty());
        Mockito.verify(registroMedicoJPA).findByAnimal_Id(id);
    }

    @Test
    void GivenNonExistentAnimalId_WhenGetRegistrosMedicosByAnimal_ThenThrowNoSuchElementException() {
        long animalId = 900;
        Mockito.when(registroMedicoJPA.findByAnimal_Id(animalId)).thenReturn(List.of());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            registroMedicoService.getRegistrosMedicosByAnimal(animalId);
        });

        Mockito.verify(registroMedicoJPA).findByAnimal_Id(animalId);
    }


    @Test
    void WhenGetAnimalesSinRevision_ThenReturnAnimales() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate inicioMes = fechaActual.withDayOfMonth(1);

        List<Long> animalesConControl = List.of(1L, 2L);

        CuidadorORM cuidador = new CuidadorORM(1L,"Juan Perez", "zooUser@zoo.com");

        EspecieORM especie1 = new EspecieORM(1L, cuidador, "Mamífero"); // `null` para el cuidador por simplicidad
        EspecieORM especie2 = new EspecieORM(2L, cuidador, "Felino");

        AnimalORM animal1 = new AnimalORM(1L, especie1, "León", 8);
        AnimalORM animal2 = new AnimalORM(2L, especie1, "Cebra", 6);
        AnimalORM animal3 = new AnimalORM(3L, especie2, "Elefante", 10);
        AnimalORM animal4 = new AnimalORM(4L, especie2, "Tigre", 5);

        List<AnimalORM> allAnimales = List.of(animal1, animal2, animal3, animal4);

        Mockito.when(registroMedicoJPA.findDistinctAnimalIdsByFechaBetween(inicioMes, fechaActual)).thenReturn(animalesConControl);
        Mockito.when(animalJPA.findAll()).thenReturn(allAnimales);
        Mockito.when(registroMedicoJPA.findUltimaFechaByAnimalId(3L)).thenReturn(LocalDate.of(2023, 12, 25));
        Mockito.when(registroMedicoJPA.findUltimaFechaByAnimalId(4L)).thenReturn(null);

        List<Map<String, Object>> animalesSinRevision = registroMedicoService.getAnimalesSinRevision();

        Assertions.assertNotNull(animalesSinRevision);
        Assertions.assertEquals(2, animalesSinRevision.size());

        Map<String, Object> animal3Data = animalesSinRevision.stream()
                .filter(animal -> animal.get("id").equals(3L))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Animal 3 no encontrado en la lista"));
        Assertions.assertEquals(3L, animal3Data.get("id"));
        Assertions.assertEquals("Elefante", animal3Data.get("nombre"));
        Assertions.assertEquals("Felino", animal3Data.get("especie"));
        Assertions.assertEquals(LocalDate.of(2023, 12, 25), animal3Data.get("ultimaFechaRevision"));

        Map<String, Object> animal4Data = animalesSinRevision.stream()
                .filter(animal -> animal.get("id").equals(4L))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Animal 4 no encontrado en la lista"));
        Assertions.assertEquals(4L, animal4Data.get("id"));
        Assertions.assertEquals("Tigre", animal4Data.get("nombre"));
        Assertions.assertEquals("Felino", animal4Data.get("especie"));
        Assertions.assertNull(animal4Data.get("ultimaFechaRevision"));

        Mockito.verify(registroMedicoJPA).findDistinctAnimalIdsByFechaBetween(inicioMes, fechaActual);
        Mockito.verify(animalJPA).findAll();
        Mockito.verify(registroMedicoJPA).findUltimaFechaByAnimalId(3L);
        Mockito.verify(registroMedicoJPA).findUltimaFechaByAnimalId(4L);
    }



    @Test
    void GivenInvalidId_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(-5L, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidAnimalId_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L,-5L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidFecha_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2025,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidEstado_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), "", "Carnivoro", "Tranquilo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), null, "Carnivoro", "Tranquilo"));
    }

    @Test
    void GivenInvalidDieta_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), "Saludable", "", "Tranquilo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), "Saludable", null, "Tranquilo"));
    }

    @Test
    void GivenInvalidComportamiento_WhenUpdateRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", ""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.updateRegistroMedico(1L, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", null));
    }

    @Test
    void GivenNonExistentAnimalId_WhenUpdateRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> registroMedicoService.updateRegistroMedico(1L, id, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));
        Mockito.verify(animalJPA).findById(id);
    }

    @Test
    void GivenNonExistentId_WhenUpdateRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Assertions.assertThrows(NoSuchElementException.class, () -> registroMedicoService.updateRegistroMedico(id, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo"));
    }

    @Test
    void WhenUpdateRegistroMedico_ThenRegistroMedicoIsUpdated() {
        long id = 1;
        long animalId = 1L;

        Mockito.when(animalJPA.findById(animalId)).thenReturn(Optional.of(new AnimalORM()));
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.of(new RegistroMedicoORM()));

        registroMedicoService.updateRegistroMedico(id, animalId, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo");

        Mockito.verify(registroMedicoJPA).save(Mockito.any(RegistroMedicoORM.class));
    }

    @Test
    void GivenInvalidId_WhenDeleteRegistroMedico_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> registroMedicoService.deleteRegistroMedico(-5L));
    }

    @Test
    void GivenNonExistentId_WhenDeleteRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(registroMedicoJPA.existsById(id)).thenReturn(false);

        Assertions.assertThrows(NoSuchElementException.class, () -> registroMedicoService.deleteRegistroMedico(id));
        Mockito.verify(registroMedicoJPA).existsById(id);
    }

    @Test
    void WhenDeleteAnimal_ThenAnimalIsDeleted() {
        long id = 1;
        Mockito.when(registroMedicoJPA.existsById(id)).thenReturn(true);

        registroMedicoService.deleteRegistroMedico(id);

        Mockito.verify(registroMedicoJPA).deleteById(id);
    }
}
