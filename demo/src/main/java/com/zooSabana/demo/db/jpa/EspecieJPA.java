package com.zooSabana.demo.db.jpa;

import com.zooSabana.demo.db.orm.EspecieORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecieJPA extends JpaRepository<EspecieORM, Long> {
}
