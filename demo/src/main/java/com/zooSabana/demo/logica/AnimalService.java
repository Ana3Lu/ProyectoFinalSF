package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AnimalService {

    private AnimalJPA animalJPA;
    private EspecieJPA especieJPA;


    public void saveAnimal(Long especie_id, String nombre, int edad) {
        if (especie_id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de animal inválido");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("Edad de animal inválida");
        }
        EspecieORM especie = especieJPA.findById(especie_id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        AnimalORM nuevoAnimal = new AnimalORM();
        nuevoAnimal.setEspecie(especie);
        nuevoAnimal.setNombre(nombre);
        nuevoAnimal.setEdad(edad);
        animalJPA.save(nuevoAnimal);
    }

    public List<AnimalORM> getAnimales() {
        return animalJPA.findAll();
    }

    public List<AnimalORM> getAnimalesByEspecie(Long especie_id) {
        if (especie_id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        List<AnimalORM> animales = animalJPA.findByEspecie_Id(especie_id);
        return animales;
    }

    public AnimalORM getAnimal(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        return animalJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
    }

    public void updateAnimal(Long id, long especie_id, String nombre, int edad) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        if (especie_id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de animal inválido");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("Edad de animal inválida");
        }
        EspecieORM especie = especieJPA.findById(especie_id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        AnimalORM animal = animalJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        animal.setEspecie(especie);
        animal.setNombre(nombre);
        animal.setEdad(edad);
        animalJPA.save(animal);
    }

    public void deleteAnimal(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        if (!animalJPA.existsById(id)) {
            throw new NoSuchElementException("Animal no encontrado");
        }
        animalJPA.deleteById(id);
    }
}
