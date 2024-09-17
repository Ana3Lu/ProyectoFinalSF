package com.zooSabana.demo.db.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate fecha;

    @Column
    private String estado;

    @Column
    private String dieta;

    @Column
    private String comportamiento;

}
