package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.AnimalDTO;
import com.zooSabana.demo.controller.dto.CuidadorDTO;
import com.zooSabana.demo.db.orm.CuidadorORM;
import com.zooSabana.demo.logica.CuidadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class CuidadorController {

    private CuidadorService cuidadorService;

    @PostMapping(path = "/cuidador")
    public ResponseEntity<String> createCuidador(@RequestBody CuidadorDTO cuidadorDTO) {
        cuidadorService.saveCuidador(cuidadorDTO.nombre(), cuidadorDTO.email());
        return ResponseEntity.status(HttpStatus.CREATED).body("Cuidador guardado exitosamente");
    }

    @GetMapping(path = "/cuidadores")
    public ResponseEntity<Object> getCuidadores() {
        return ResponseEntity.status(HttpStatus.OK).body(cuidadorService.getCuidadores());
    }

    @GetMapping(path = "/cuidadores/{id}")
    public ResponseEntity<Object> getCuidadorById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(cuidadorService.getCuidador(id));
    }

    @PutMapping(path = "/cuidadores/{id}")
    public ResponseEntity<String> updateCuidador(@PathVariable Long id, @RequestBody CuidadorDTO cuidadorDTO) {
        cuidadorService.updateCuidador(id, cuidadorDTO.nombre(), cuidadorDTO.email());
        return ResponseEntity.status(HttpStatus.OK).body("Cuidador actualizado exitosamente");
    }

    @DeleteMapping(path = "/cuidadores/{id}")
    public ResponseEntity<String> deleteCuidador(@PathVariable Long id) {
        cuidadorService.deleteCuidador(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cuidador eliminado exitosamente");
    }
}
