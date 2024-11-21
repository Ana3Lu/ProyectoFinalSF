package com.zooSabana.demo.db.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cuidadores")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuidadorORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String email;
}
