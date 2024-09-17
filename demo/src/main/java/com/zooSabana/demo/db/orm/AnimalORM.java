package com.zooSabana.demo.db.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "animales")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "especieId")
    private EspecieORM especie;

    @Column
    private String nombre;

    @Column
    private int edad;

}
