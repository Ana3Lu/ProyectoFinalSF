package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.orm.CuidadorORM;
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
public class CuidadorServiceTest {

    @Mock
    CuidadorJPA cuidadorJPA;

    @InjectMocks
    CuidadorService cuidadorService;

    @Test
    void GivenInvalidNombre_WhenSaveCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.saveCuidador("","andreaLp@hotmail.com"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.saveCuidador(null,"andreaLp@hotmail.com"));
    }

    @Test
    void GivenInvalidEmail_WhenSaveCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.saveCuidador("Andrea Lopez",""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.saveCuidador("Andrea Lopez",null));
    }

    @Test
    void WhenSaveCuidador_ThenCuidadorIsSaved() {
        Mockito.when(cuidadorJPA.save(Mockito.any(CuidadorORM.class))).thenReturn(new CuidadorORM());

        cuidadorService.saveCuidador("Andrea Lopez", "andreaLp@hotmail.com");

        Mockito.verify(cuidadorJPA).save(Mockito.any(CuidadorORM.class));
    }

    @Test
    void WhenGetCuidadores_ThenReturnCuidadores() {
        List<CuidadorORM> cuidadores = new ArrayList<>();
        cuidadores.add(new CuidadorORM());
        Mockito.when(cuidadorJPA.findAll()).thenReturn(cuidadores);

        List<CuidadorORM> cuidadoresObtenidos = cuidadorService.getCuidadores();

        Assertions.assertFalse(cuidadoresObtenidos.isEmpty());
        Mockito.verify(cuidadorJPA).findAll();
    }

    @Test
    void GivenInvalidId_WhenGetCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.getCuidador(-5L));
    }

    @Test
    void GivenNonExistentId_WhenGetCuidador_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(cuidadorJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> cuidadorService.getCuidador(id));
        Mockito.verify(cuidadorJPA).findById(id);
    }

    @Test
    void WhenGetCuidador_ThenReturnCuidador() {
        long id = 1;
        Mockito.when(cuidadorJPA.findById(id)).thenReturn(Optional.of(new CuidadorORM()));

        CuidadorORM cuidador = cuidadorService.getCuidador(id);

        Assertions.assertNotNull(cuidador);
        Mockito.verify(cuidadorJPA).findById(id);
    }

    @Test
    void GivenInvalidId_WhenUpdateCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.updateCuidador(-5L, "Nicolas Lopez", "nicolasLp@gmail.com"));
    }

    @Test
    void GivenNonExistentId_WhenUpdateCuidador_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(cuidadorJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> cuidadorService.updateCuidador(id, "Nicolas Lopez", "nicolasLp@gmail.com"));
        Mockito.verify(cuidadorJPA).findById(id);
    }

    @Test
    void GivenInvalidNombre_WhenUpdateCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.updateCuidador(1L, "", "nicolasLp@gmail.com"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.updateCuidador(1L, null, "nicolasLp@gmail.com"));
    }

    @Test
    void GivenInvalidEmail_WhenUpdateCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.updateCuidador(1L, "Nicolas Lopez", ""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.updateCuidador(1L, "Nicolas Lopez", null));
    }

    @Test
    void WhenUpdateCuidador_ThenCuidadorIsUpdated() {
        long id = 1;
        Mockito.when(cuidadorJPA.findById(id)).thenReturn(Optional.of(new CuidadorORM()));

        cuidadorService.updateCuidador(id, "Nicolas Lopez", "nicolasLp@gmail.com");

        Mockito.verify(cuidadorJPA).save(Mockito.any(CuidadorORM.class));
    }

    @Test
    void GivenInvalidId_WhenDeleteCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> cuidadorService.deleteCuidador(-5L));
    }

    @Test
    void GivenNonExistentId_WhenDeleteCuidador_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(cuidadorJPA.existsById(id)).thenReturn(false);

        Assertions.assertThrows(NoSuchElementException.class, () -> cuidadorService.deleteCuidador(id));
        Mockito.verify(cuidadorJPA).existsById(id);
    }

    @Test
    void WhenDeleteCuidador_ThenCuidadorIsDeleted() {
        long id = 1;
        Mockito.when(cuidadorJPA.existsById(id)).thenReturn(true);

        cuidadorService.deleteCuidador(id);

        Mockito.verify(cuidadorJPA).deleteById(id);
    }
}
