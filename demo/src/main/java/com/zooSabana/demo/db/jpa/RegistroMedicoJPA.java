package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RegistroMedicoJPA extends JpaRepository<RegistroMedicoORM, Long> {

    @Query("SELECT r FROM RegistroMedicoORM r WHERE r.animal.id = :animal_id")
    List<RegistroMedicoORM> findByAnimal_Id(Long animal_id);

    @Query("SELECT DISTINCT r.animal.id FROM RegistroMedicoORM r WHERE r.fecha BETWEEN :inicio AND :fin")
    List<Long> findDistinctAnimalIdsByFechaBetween(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT MAX(r.fecha) FROM RegistroMedicoORM r WHERE r.animal.id = :animal_id")
    LocalDate findUltimaFechaByAnimalId(Long animal_id);
}
