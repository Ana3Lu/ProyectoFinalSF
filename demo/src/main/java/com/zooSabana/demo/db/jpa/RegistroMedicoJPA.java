package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroMedicoJPA extends JpaRepository<RegistroMedicoORM, Long> {

    List<RegistroMedicoORM> findByAnimal_Id(Long animal_id);

    List<Long> findDistinctAnimalIdsByFechaBetween(LocalDate inicio, LocalDate fin);
}
