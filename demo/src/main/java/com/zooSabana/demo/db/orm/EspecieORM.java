package com.zooSabana.demo.db.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "especies")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecieORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuidador_id")
    private CuidadorORM cuidador;

    @Column
    private String nombre;
}
