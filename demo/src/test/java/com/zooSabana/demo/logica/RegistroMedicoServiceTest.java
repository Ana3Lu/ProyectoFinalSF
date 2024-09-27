package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.RegistroMedicoJPA;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Mockito.when(registroMedicoJPA.save(Mockito.any(RegistroMedicoORM.class))).thenReturn(new RegistroMedicoORM());

        registroMedicoService.saveRegistroMedico(1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo");

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

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            registroMedicoService.getRegistroMedico(id);
        });
        Mockito.verify(registroMedicoJPA).findById(id);
    }

    @Test
    void WhenGetRegistroMedico_ThenReturnRegistroMedico() {
        long id = 1;
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.of(new RegistroMedicoORM()));

        RegistroMedicoORM registroMedico = registroMedicoService.getRegistroMedico(id);

        Assertions.assertNotNull(registroMedico);
        Mockito.verify(registroMedicoJPA.findById(id));
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

    /*@Test
    void GivenNonExistentEspecieId_WhenGetAnimalesByEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            RegistroMedicoService.getRegistrosMedicosByAnimal(id);
        });
        Mockito.verify(animalJPA).findById(id);
    }*/

    @Test
    void WhenGetAnimalesSinRevision_ThenReturnAnimalesId() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate inicioMes = fechaActual.withDayOfMonth(1);

        List<Long> animalesConControl = new ArrayList<>();
        animalesConControl.add(1L);
        animalesConControl.add(2L);

        List<Long> allAnimales = new ArrayList<>();
        allAnimales.add(1L);
        allAnimales.add(2L);
        allAnimales.add(3L);
        allAnimales.add(4L);

        Mockito.when(registroMedicoJPA.findDistinctAnimalIdsByFechaBetween(inicioMes, fechaActual)).thenReturn(animalesConControl);
        Mockito.when(animalJPA.findAllAnimalIds()).thenReturn(allAnimales);

        List<Long> animalesSinRevision = registroMedicoService.getAnimalesSinRevision();

        Assertions.assertNotNull(animalesSinRevision);
        Assertions.assertEquals(2, animalesSinRevision.size());
        Assertions.assertTrue(animalesSinRevision.contains(3L));
        Assertions.assertTrue(animalesSinRevision.contains(4L));

        Mockito.verify(registroMedicoJPA).findDistinctAnimalIdsByFechaBetween(inicioMes, fechaActual);
        Mockito.verify(animalJPA).findAllAnimalIds();
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

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            registroMedicoService.updateRegistroMedico(1L, id, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo");
        });
        Mockito.verify(animalJPA).findById(id);
    }

    @Test
    void GivenNonExistentId_WhenUpdateRegistroMedico_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            registroMedicoService.updateRegistroMedico(id, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo");
        });
        Mockito.verify(registroMedicoJPA).findById(id);
    }

    @Test
    void WhenUpdateRegistroMedico_ThenRegistroMedicoIsUpdated() {
        long id = 1;
        Mockito.when(registroMedicoJPA.findById(id)).thenReturn(Optional.of(new RegistroMedicoORM()));

        registroMedicoService.updateRegistroMedico(id, 1L, LocalDate.of(2023,3,1), "Saludable", "Carnivoro", "Tranquilo");

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

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            registroMedicoService.deleteRegistroMedico(id);
        });
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
