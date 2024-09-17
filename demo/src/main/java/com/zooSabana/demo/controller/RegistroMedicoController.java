package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.RegistroMedicoDTO;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import com.zooSabana.demo.logica.RegistroMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@AllArgsConstructor
public class RegistroMedicoController {

    private RegistroMedicoService registroMedicoService;


    @PostMapping(path = "/registro-medico")
    public ResponseEntity<String> createRegistroMedico(@RequestBody RegistroMedicoDTO registroMedicoDTO) {
        try {
            registroMedicoService.saveRegistroMedico(registroMedicoDTO.animalId(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
            return ResponseEntity.status(HttpStatus.CREATED).body("Registro médico guardado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos")
    public ResponseEntity<List<RegistroMedicoORM>> getAllRegistrosMedicos() {
        List<RegistroMedicoORM> registros = registroMedicoService.getAllRegistrosMedicos();
        return ResponseEntity.status(HttpStatus.OK).body(registros);
    }

    @GetMapping(path = "/registros-medicos/{id}")
    public ResponseEntity<Object> getRegistroMedico(@RequestParam Long id) {
        try {
            RegistroMedicoORM registro = registroMedicoService.getRegistroMedico(id);
            return ResponseEntity.status(HttpStatus.OK).body(registro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos/animal/{animalId}")
    public ResponseEntity<Object> getRegistrosMedicosByAnimal(@PathVariable Long animalId) {
        try {
            List<RegistroMedicoORM> registrosMedicos = registroMedicoService.getRegistrosMedicos(animalId);
            return ResponseEntity.status(HttpStatus.OK).body(registrosMedicos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos/animales-sin-revision/fecha/{fecha}")
    public ResponseEntity<Object> getAnimalesSinRevision(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") LocalDate fecha) {
        try {
            List<Long> animales = registroMedicoService.getAnimalesSinRevision(fecha);
            return ResponseEntity.status(HttpStatus.OK).body(animales);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/registro-medico/{id}")
    public ResponseEntity<String> updateRegistroMedico(@PathVariable Long id, @RequestBody RegistroMedicoDTO registroMedicoDTO) {
        try {
            registroMedicoService.updateRegistroMedico(id, registroMedicoDTO.animalId(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
            return ResponseEntity.status(HttpStatus.OK).body("Registro médico actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/registro-medico/{id}")
    public String deleteRegistroMedico(@PathVariable Long id) {
        try {
            registroMedicoService.deleteRegistroMedico(id);
            return "Registro medico eliminado exitosamente";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}
