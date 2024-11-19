package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.CuidadorORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuidadorJPA extends JpaRepository<CuidadorORM, Long> {
}
