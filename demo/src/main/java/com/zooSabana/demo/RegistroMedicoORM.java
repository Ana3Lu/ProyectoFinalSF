package com.zooSabana.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Table(name = "registros_medicos")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroMedicoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animalId")
    private AnimalORM animal;

    @Column
    private YearMonth fecha;

    @Column
    private String estado;

    @Column
    private String dieta;

    @Column
    private String comportamiento;

}
