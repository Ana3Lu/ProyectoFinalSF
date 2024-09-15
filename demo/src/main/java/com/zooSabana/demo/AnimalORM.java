package com.zooSabana.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "animales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long especieId;

    @Column
    private String nombre;

    @Column
    private int edad;

}
