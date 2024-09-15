package com.zooSabana.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "registros_medicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroMedicoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long animalId;

    @Column
    private int fecha;

    @Column
    private String estado;

    @Column
    private String dieta;

    @Column
    private String comportamiento;

}
