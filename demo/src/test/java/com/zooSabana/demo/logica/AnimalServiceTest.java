package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    AnimalJPA animalJPA;

    @Mock
    EspecieJPA especieJPA;

    @InjectMocks
    AnimalService animalService;

    @Test
    void GivenInvalidEspecieId_WhenSaveAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.saveAnimal(-5L, "Panda", 1));
    }

    @Test
    void GivenInvalidNombre_WhenSaveAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.saveAnimal(1L, "", 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.saveAnimal(1L, null, 1));
    }

    @Test
    void GivenInvalidEdad_WhenSaveAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.saveAnimal(1L, "Panda", -1));
    }

    @Test
    void GivenNonExistentEspecieId_WhenSaveAnimal_ThenThrowNoSuchElementException() {
        long id = 900;
        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.saveAnimal(id, "Panda", 1));
    }

    @Test
    void WhenSaveAnimal_ThenAnimalIsSaved() {
        long especieId = 1L;
        Mockito.when(especieJPA.findById(especieId)).thenReturn(Optional.of(new EspecieORM()));
        Mockito.when(animalJPA.save(Mockito.any(AnimalORM.class))).thenReturn(new AnimalORM());

        animalService.saveAnimal(especieId, "Panda", 1);

        Mockito.verify(animalJPA).save(Mockito.any(AnimalORM.class));
    }

    @Test
    void WhenGetAnimales_ThenReturnAnimales() {
        List<AnimalORM> animales = new ArrayList<>();
        animales.add(new AnimalORM());
        Mockito.when(animalJPA.findAll()).thenReturn(animales);

        List<AnimalORM> animalesObtenidos = animalService.getAnimales();

        Assertions.assertFalse(animalesObtenidos.isEmpty());
        Mockito.verify(animalJPA).findAll();
    }

    @Test
    void GivenInvalidEspecieId_WhenGetAnimalesByEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.getAnimalesByEspecie(-5L));
    }

    /*@Test
    void GivenNonExistentEspecieId_WhenGetAnimalesByEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(especieJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            animalService.getAnimalesByEspecie(id);
        });
        Mockito.verify(especieJPA).findById(id);
    }*/

    @Test
    void WhenGetAnimalesByEspecie_ThenReturnAnimales() {
        long id = 1;
        ArrayList<AnimalORM> animales = new ArrayList<>();
        animales.add(new AnimalORM());
        Mockito.when(animalJPA.findByEspecie_Id(id)).thenReturn(animales);

        List<AnimalORM> animalesObtenidos = animalService.getAnimalesByEspecie(id);

        Assertions.assertFalse(animalesObtenidos.isEmpty());
        Mockito.verify(animalJPA).findByEspecie_Id(id);
    }

    @Test
    void GivenInvalidId_WhenGetAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.getAnimal(-5L));
    }

    @Test
    void GivenNonExistentId_WhenGetAnimal_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.getAnimal(id));
        Mockito.verify(animalJPA).findById(id);
    }

    @Test
    void WhenGetAnimal_ThenReturnAnimal() {
        long id = 1;
        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.of(new AnimalORM()));

        AnimalORM animalObtenido = animalService.getAnimal(id);

        Assertions.assertNotNull(animalObtenido);
        Mockito.verify(animalJPA).findById(id);
    }

    @Test
    void GivenInvalidId_WhenUpdateAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.updateAnimal(-5L, 1L,"Panda", 1));
    }

    @Test
    void GivenInvalidEspecieId_WhenUpdateAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.updateAnimal(1L,-5L, "Panda", 1));
    }

    @Test
    void GivenInvalidNombre_WhenUpdateAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.updateAnimal(1L, 1L, "", 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.updateAnimal(1L, 1L, null, 1));
    }

    @Test
    void GivenInvalidEdad_WhenUpdateAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.updateAnimal(1L, 1L, "Panda", -1));
    }

    @Test
    void GivenNonExistentEspecieId_WhenUpdateAnimal_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(especieJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.updateAnimal(1L, id, "Panda", 1));
        Mockito.verify(especieJPA).findById(id);
    }

    @Test
    void GivenNonExistentId_WhenUpdateAnimal_ThenThrowNoSuchElementException() {
        long id = 900;

        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.updateAnimal(id, 1L, "Panda",1));
    }

    @Test
    void WhenUpdateAnimal_ThenAnimalIsUpdated() {
        long id = 1;
        long especieId = 1L;

        Mockito.when(animalJPA.findById(id)).thenReturn(Optional.of(new AnimalORM()));
        Mockito.when(especieJPA.findById(especieId)).thenReturn(Optional.of(new EspecieORM()));

        animalService.updateAnimal(id, especieId, "Panda", 1);

        Mockito.verify(animalJPA).save(Mockito.any(AnimalORM.class));
    }

    @Test
    void GivenInvalidId_WhenDeleteAnimal_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> animalService.deleteAnimal(-5L));
    }

    @Test
    void GivenNonExistentId_WhenDeleteAnimal_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(animalJPA.existsById(id)).thenReturn(false);

        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.deleteAnimal(id));
        Mockito.verify(animalJPA).existsById(id);
    }

    @Test
    void WhenDeleteAnimal_ThenAnimalIsDeleted() {
        long id = 1;
        Mockito.when(animalJPA.existsById(id)).thenReturn(true);

        animalService.deleteAnimal(id);

        Mockito.verify(animalJPA).deleteById(id);
    }
}
