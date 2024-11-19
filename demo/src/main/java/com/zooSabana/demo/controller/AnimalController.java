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

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class AnimalController {

    private AnimalService animalService;


    @PostMapping(path = "/animal")
    public ResponseEntity<String> createAnimal(@RequestBody AnimalDTO animalDTO) {
        try {
            animalService.saveAnimal(animalDTO.especie_id(), animalDTO.nombre(), animalDTO.edad());
            return ResponseEntity.status(HttpStatus.CREATED).body("Animal guardado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/animales")
    public ResponseEntity<Object> getAnimales() {
        List<AnimalORM> animales = animalService.getAnimales();
        return ResponseEntity.status(HttpStatus.OK).body(animales);
    }

    @GetMapping(path = "/animales-especie")
    public ResponseEntity<Object> getAnimalesByEspecie(@RequestParam Long especie_id) {
        try {
            List<AnimalORM> animales = animalService.getAnimalesByEspecie(especie_id);
            return ResponseEntity.status(HttpStatus.OK).body(animales);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/animales/{id}")
    public ResponseEntity<Object> getAnimalById(@PathVariable Long id) {
        try {
            AnimalORM animal = animalService.getAnimal(id);
            return ResponseEntity.status(HttpStatus.OK).body(animal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/animales/{id}")
    public ResponseEntity<String> updateAnimal(@PathVariable Long id, @RequestBody AnimalDTO animalDTO) {
      try {
          animalService.updateAnimal(id, animalDTO.especie_id(), animalDTO.nombre(), animalDTO.edad());
          return ResponseEntity.status(HttpStatus.OK).body("Animal actualizado exitosamente");
      } catch (IllegalArgumentException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } catch (NoSuchElementException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    }

    @DeleteMapping(path = "/animales/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {
        try {
            animalService.deleteAnimal(id);
            return ResponseEntity.status(HttpStatus.OK).body("Animal eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
