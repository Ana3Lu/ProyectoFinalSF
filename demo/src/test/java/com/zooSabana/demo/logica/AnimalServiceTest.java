package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
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

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    AnimalJPA animalJPA;

    @InjectMocks
    AnimalService animalService;

    @Test
    void GivenInvalidAnimalId_WhenGetAnimalById_ThenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.getAnimal(-5L));
    }

    @Test
    void GivenInvalidAnimalId_WhenGetAnimalesByEspecie_ThenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> animalService.getAnimalesByEspecie(-5L));
    }
}
