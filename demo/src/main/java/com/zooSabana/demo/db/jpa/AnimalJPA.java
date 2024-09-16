package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.AnimalORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalJPA extends JpaRepository<AnimalORM, Long> {
}
