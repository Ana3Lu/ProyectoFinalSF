package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.logica.EspecieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class EspecieController {

    private EspecieService especieService;

    @PostMapping(path = "/especie")
    public ResponseEntity<String> createAnimal(@RequestBody EspecieDTO especieDTO) {
        try {
            especieService.saveEspecie(especieDTO.nombre());
            return ResponseEntity.status(HttpStatus.CREATED).body("Especie guardada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/especies/{id}")
    public ResponseEntity<Object> getEspecieById(@PathVariable Long id) {
        try {
            EspecieORM especie = especieService.getEspecie(id);
            return ResponseEntity.status(HttpStatus.OK).body(especie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/especies/{id}")
    public ResponseEntity<String> updateEspecie(@PathVariable Long id, @RequestParam String nombre) {
        try {
            especieService.updateEspecie(id, nombre);
            return ResponseEntity.status(HttpStatus.OK).body("Especie actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/especie/{id}")
    public ResponseEntity<String> deleteEspecie(@PathVariable Long id) {
        try {
            especieService.deleteEspecie(id);
            return ResponseEntity.status(HttpStatus.OK).body("Especie eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
