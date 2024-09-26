package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.EspecieJPA;
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

@ExtendWith(MockitoExtension.class)
public class EspecieServiceTest {

    @Mock
    EspecieJPA especieJPA;

    @InjectMocks
    EspecieService especieService;

    @Test
    void GivenInvalidEspecieId_WhenGetEspecieById_ThenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> especieService.getEspecieById(-5L));
    }
}
