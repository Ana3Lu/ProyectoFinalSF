package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.RegistroMedicoDTO;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import com.zooSabana.demo.logica.RegistroMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class RegistroMedicoController {

    private RegistroMedicoService registroMedicoService;


    @PostMapping(path = "/registro-medico")
    public ResponseEntity<String> createRegistroMedico(@RequestBody RegistroMedicoDTO registroMedicoDTO) {
        try {
            registroMedicoService.saveRegistroMedico(registroMedicoDTO.animal_id(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
            return ResponseEntity.status(HttpStatus.CREATED).body("Registro médico guardado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos")
    public ResponseEntity<List<RegistroMedicoORM>> getRegistrosMedicos() {
        List<RegistroMedicoORM> registros = registroMedicoService.getRegistrosMedicos();
        return ResponseEntity.status(HttpStatus.OK).body(registros);
    }

    @GetMapping(path = "/registros-medicos/{id}")
    public ResponseEntity<Object> getRegistroMedicoById(@PathVariable Long id) {
        try {
            RegistroMedicoORM registro = registroMedicoService.getRegistroMedico(id);
            return ResponseEntity.status(HttpStatus.OK).body(registro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos/animales/{animal_id}")
    public ResponseEntity<Object> getRegistrosMedicosByAnimal(@PathVariable Long animal_id) {
        try {
            List<RegistroMedicoORM> registrosMedicos = registroMedicoService.getRegistrosMedicosByAnimal(animal_id);
            return ResponseEntity.status(HttpStatus.OK).body(registrosMedicos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/registros-medicos/animales/revision-pendiente-mes")
    public ResponseEntity<Object> getAnimalesIdSinRevision() {
        List<Long> animales = registroMedicoService.getAnimalesSinRevision();
        return ResponseEntity.status(HttpStatus.OK).body(animales);

    }

    @PutMapping(path = "/registro-medico/{id}")
    public ResponseEntity<String> updateRegistroMedico(@PathVariable Long id, @RequestBody RegistroMedicoDTO registroMedicoDTO) {
        try {
            registroMedicoService.updateRegistroMedico(id, registroMedicoDTO.animal_id(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
            return ResponseEntity.status(HttpStatus.OK).body("Registro médico actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/registro-medico/{id}")
    public ResponseEntity<String> deleteRegistroMedico(@PathVariable Long id) {
        try {
            registroMedicoService.deleteRegistroMedico(id);
            return ResponseEntity.status(HttpStatus.OK).body("Registro médico eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
