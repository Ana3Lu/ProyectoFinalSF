package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.AnimalDTO;
import com.zooSabana.demo.controller.dto.EspecieDTO;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import com.zooSabana.demo.logica.EspecieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class EspecieController {

    private EspecieService especieService;

    @PostMapping(path = "/especie")
    public ResponseEntity<String> createEspecie(@RequestBody EspecieDTO especieDTO) {
        try {
            especieService.saveEspecie(especieDTO.cuidador_id(), especieDTO.nombre());
            return ResponseEntity.status(HttpStatus.CREATED).body("Especie guardada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/especies")
    public ResponseEntity<Object> getEspecies() {
        List<EspecieORM> especies = especieService.getEspecies();
        return ResponseEntity.status(HttpStatus.OK).body(especies);
    }

    @GetMapping(path = "/especies-cuidador")
    public ResponseEntity<Object> getEspeciesByCuidador(@RequestParam Long cuidador_id) {
        try {
            List<EspecieORM> especies = especieService.getEspeciesByCuidador(cuidador_id);
            return ResponseEntity.status(HttpStatus.OK).body(especies);
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
    public ResponseEntity<String> updateEspecie(@PathVariable Long id, @RequestBody EspecieDTO especieDTO) {
        try {
            especieService.updateEspecie(id, especieDTO.cuidador_id(), especieDTO.nombre());
            return ResponseEntity.status(HttpStatus.OK).body("Especie actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/especies/{id}")
    public ResponseEntity<String> deleteEspecie(@PathVariable Long id) {
        try {
            especieService.deleteEspecie(id);
            return ResponseEntity.status(HttpStatus.OK).body("Especie eliminada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
