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


    public void saveAnimal(Long especieId, String nombre, int edad) {
        if (especieId < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("Edad de animal inválida");
        }
        EspecieORM especie = especieJPA.findById(especieId)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        AnimalORM nuevoAnimal = new AnimalORM();
        nuevoAnimal.setEspecie(especie);
        nuevoAnimal.setNombre(nombre);
        nuevoAnimal.setEdad(edad);
        animalJPA.save(nuevoAnimal);
    }

    public List<AnimalORM> getAnimales(Long especieId) {
        if (especieId < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        EspecieORM especie = especieJPA.findById(especieId)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        return animalJPA.findAll()
                .stream()
                .filter(animal -> especie.equals(animal.getEspecie()))
                .toList();
    }

    public AnimalORM getAnimal(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        return animalJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
    }

    public void updateAnimal(Long id, long especieId, String nombre, int edad) {
        if (especieId < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("Edad de animal inválida");
        }
        EspecieORM especie = especieJPA.findById(especieId)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        AnimalORM animal = animalJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        animal.setEspecie(especie);
        animal.setNombre(nombre);
        animal.setEdad(edad);
        animalJPA.save(animal);
    }

    public void deleteAnimal(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        if (!animalJPA.existsById(id)) {
            throw new NoSuchElementException("Animal no encontrado");
        }
        animalJPA.deleteById(id);
    }
}
