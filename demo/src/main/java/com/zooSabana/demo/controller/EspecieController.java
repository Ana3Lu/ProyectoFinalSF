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

@RestController
@AllArgsConstructor
public class EspecieController {

    private EspecieService especieService;

    @PostMapping(path = "/especie")
    public ResponseEntity<String> createEspecie(@RequestBody EspecieDTO especieDTO) {
        especieService.saveEspecie(especieDTO.cuidador_id(), especieDTO.nombre());
        return ResponseEntity.status(HttpStatus.CREATED).body("Especie guardada exitosamente");
    }

    @GetMapping(path = "/especies")
    public ResponseEntity<Object> getEspecies() {
        return ResponseEntity.status(HttpStatus.OK).body(especieService.getEspecies());
    }

    @GetMapping(path = "/especies-cuidador")
    public ResponseEntity<Object> getEspeciesByCuidador(@RequestParam Long cuidador_id) {
        return ResponseEntity.status(HttpStatus.OK).body(especieService.getEspeciesByCuidador(cuidador_id));
    }

    @GetMapping(path = "/especies/{id}")
    public ResponseEntity<Object> getEspecieById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(especieService.getEspecie(id));
    }

    @PutMapping(path = "/especies/{id}")
    public ResponseEntity<String> updateEspecie(@PathVariable Long id, @RequestBody EspecieDTO especieDTO) {
        especieService.updateEspecie(id, especieDTO.cuidador_id(), especieDTO.nombre());
        return ResponseEntity.status(HttpStatus.OK).body("Especie actualizada exitosamente");
    }

    @DeleteMapping(path = "/especies/{id}")
    public ResponseEntity<String> deleteEspecie(@PathVariable Long id) {
        especieService.deleteEspecie(id);
        return ResponseEntity.status(HttpStatus.OK).body("Especie eliminada exitosamente");
    }
}
