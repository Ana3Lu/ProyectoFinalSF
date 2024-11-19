package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecieJPA extends JpaRepository<EspecieORM, Long> {

    List<EspecieORM> findByCuidador_Id(Long cuidador_id);
}
