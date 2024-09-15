package com.zooSabana.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalJPA extends JpaRepository<AnimalORM, Long> {
}
