package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroMedicoJPA extends JpaRepository<RegistroMedicoORM, Long> {
}
