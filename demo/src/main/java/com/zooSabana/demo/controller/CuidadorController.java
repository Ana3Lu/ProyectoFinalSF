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

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class CuidadorController {

    private CuidadorService cuidadorService;

    @PostMapping(path = "/cuidador")
    public ResponseEntity<String> createCuidador(@RequestBody CuidadorDTO cuidadorDTO) {
        try {
            cuidadorService.saveCuidador(cuidadorDTO.nombre(), cuidadorDTO.email());
            return ResponseEntity.status(HttpStatus.CREATED).body("Cuidador guardado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/cuidadores")
    public ResponseEntity<Object> getEspecies() {
        List<CuidadorORM> cuidadores = cuidadorService.getCuidadores();
        return ResponseEntity.status(HttpStatus.OK).body(cuidadores);
    }

    @GetMapping(path = "/cuidadores/{id}")
    public ResponseEntity<Object> getCuidadorById(@PathVariable Long id) {
        try {
            CuidadorORM cuidador = cuidadorService.getCuidador(id);
            return ResponseEntity.status(HttpStatus.OK).body(cuidador);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/cuidadores/{id}")
    public ResponseEntity<String> updateCuidador(@PathVariable Long id, @RequestBody CuidadorDTO cuidadorDTO) {
        try {
            cuidadorService.updateCuidador(id, cuidadorDTO.nombre(), cuidadorDTO.email());
            return ResponseEntity.status(HttpStatus.OK).body("Cuidador actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/cuidadores/{id}")
    public ResponseEntity<String> deleteCuidador(@PathVariable Long id) {
        try {
            cuidadorService.deleteCuidador(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cuidador eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
