package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.CuidadorORM;
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
public class EspecieServiceTest {

    @Mock
    EspecieJPA especieJPA;

    @Mock
    CuidadorJPA cuidadorJPA;

    @InjectMocks
    EspecieService especieService;

    @Test
    void GivenInvalidCuidadorId_WhenSaveEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.saveEspecie(-5L, "Acuatico"));
    }

    @Test
    void GivenInvalidNombre_WhenSaveEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.saveEspecie(1L, ""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.saveEspecie(1L, null));
    }

    @Test
    void GivenNonExistentCuidadorId_WhenSaveEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.saveEspecie(id, "Acuatico"));
    }

    @Test
    void WhenSaveEspecie_ThenEspecieIsSaved() {
        long cuidadorId = 1L;
        Mockito.when(cuidadorJPA.findById(cuidadorId)).thenReturn(Optional.of(new CuidadorORM()));
        Mockito.when(especieJPA.save(Mockito.any(EspecieORM.class))).thenReturn(new EspecieORM());

        especieService.saveEspecie(cuidadorId, "Acuatico");

        Mockito.verify(especieJPA).save(Mockito.any(EspecieORM.class));
    }

    @Test
    void WhenGetEspecies_ThenReturnEspecies() {
        List<EspecieORM> especies = new ArrayList<>();
        especies.add(new EspecieORM());
        Mockito.when(especieJPA.findAll()).thenReturn(especies);

        List<EspecieORM> especiesObtenidas = especieService.getEspecies();

        Assertions.assertFalse(especiesObtenidas.isEmpty());
        Mockito.verify(especieJPA).findAll();
    }

    @Test
    void GivenInvalidCuidadorId_WhenGetEspeciesByCuidador_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.getEspeciesByCuidador(-5L));
    }

    @Test
    void WhenGetEspeciesByCuidador_ThenReturnEspecies() {
        long id = 1;
        ArrayList<EspecieORM> especies = new ArrayList<>();
        especies.add(new EspecieORM());
        Mockito.when(especieJPA.findByCuidador_Id(id)).thenReturn(especies);

        List<EspecieORM> especiesObtenidas = especieService.getEspeciesByCuidador(id);

        Assertions.assertFalse(especiesObtenidas.isEmpty());
        Mockito.verify(especieJPA).findByCuidador_Id(id);
    }

    @Test
    void GivenInvalidId_WhenGetEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.getEspecie(-5L));
    }

    @Test
    void GivenNonExistentId_WhenGetEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(especieJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.getEspecie(id));
        Mockito.verify(especieJPA).findById(id);
    }

    @Test
    void WhenGetEspecie_ThenReturnEspecie() {
        long id = 1;
        Mockito.when(especieJPA.findById(id)).thenReturn(Optional.of(new EspecieORM()));

        EspecieORM especie = especieService.getEspecie(id);

        Assertions.assertNotNull(especie);
        Mockito.verify(especieJPA).findById(id);
    }

    @Test
    void GivenInvalidId_WhenUpdateEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.updateEspecie(-5L, 1L, "Acuatico"));
    }

    @Test
    void GivenInvalidCuidadorId_WhenUpdateEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.updateEspecie(1L,-5L, "Acuatico"));
    }

    @Test
    void GivenInvalidNombre_WhenUpdateEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.updateEspecie(1L, 1L, ""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.updateEspecie(1L, 1L, null));
    }

    @Test
    void GivenNonExistentCuidadorId_WhenUpdateEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(cuidadorJPA.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.updateEspecie(1L, id, "Acuatico"));
        Mockito.verify(cuidadorJPA).findById(id);
    }

    @Test
    void GivenNonExistentId_WhenUpdateEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.updateEspecie(id, 1L, "Acuatico"));
    }

    @Test
    void WhenUpdateEspecie_ThenEspecieIsUpdated() {
        long id = 1;
        long cuidadorId = 1L;

        Mockito.when(especieJPA.findById(id)).thenReturn(Optional.of(new EspecieORM()));
        Mockito.when(cuidadorJPA.findById(cuidadorId)).thenReturn(Optional.of(new CuidadorORM()));

        especieService.updateEspecie(id, cuidadorId, "Mamifero");

        Mockito.verify(especieJPA).save(Mockito.any(EspecieORM.class));
    }

    @Test
    void GivenInvalidId_WhenDeleteEspecie_ThenThrowIllegalException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> especieService.deleteEspecie(-5L));
    }

    @Test
    void GivenNonExistentId_WhenDeleteEspecie_ThenThrowNoSuchElementException() {
        long id = 900;
        Mockito.when(especieJPA.existsById(id)).thenReturn(false);

        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.deleteEspecie(id));
        Mockito.verify(especieJPA).existsById(id);
    }

    @Test
    void WhenDeleteEspecie_ThenEspecieIsDeleted() {
        long id = 1;
        Mockito.when(especieJPA.existsById(id)).thenReturn(true);

        especieService.deleteEspecie(id);

        Mockito.verify(especieJPA).deleteById(id);
    }
}
