package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.AnimalORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalJPA extends JpaRepository<AnimalORM, Long> {

    @Query("SELECT a.id FROM AnimalORM a")
    List<Long> findAllAnimalIds();

    List<AnimalORM> findByEspecie_Id(Long especie_id);
}
