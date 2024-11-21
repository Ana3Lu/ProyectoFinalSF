package com.zooSabana.demo.controller;

import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.logica.AnimalService;
import com.zooSabana.demo.controller.dto.AnimalDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class AnimalController {

    private AnimalService animalService;

    @PostMapping(path = "/animal")
    public ResponseEntity<String> createAnimal(@RequestBody AnimalDTO animalDTO) {
        animalService.saveAnimal(animalDTO.especie_id(), animalDTO.nombre(), animalDTO.edad());
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal guardado exitosamente");
    }

    @GetMapping(path = "/animales")
    public ResponseEntity<Object> getAnimales() {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAnimales());
    }

    @GetMapping(path = "/animales-especie")
    public ResponseEntity<Object> getAnimalesByEspecie(@RequestParam Long especie_id) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAnimalesByEspecie(especie_id));
    }

    @GetMapping(path = "/animales/{id}")
    public ResponseEntity<Object> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAnimal(id));
    }

    @PutMapping(path = "/animales/{id}")
    public ResponseEntity<String> updateAnimal(@PathVariable Long id, @RequestBody AnimalDTO animalDTO) {
        animalService.updateAnimal(id, animalDTO.especie_id(), animalDTO.nombre(), animalDTO.edad());
        return ResponseEntity.status(HttpStatus.OK).body("Animal actualizado exitosamente");
    }

    @DeleteMapping(path = "/animales/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.status(HttpStatus.OK).body("Animal eliminado exitosamente");
    }
}
