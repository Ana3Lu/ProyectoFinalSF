package com.zooSabana.demo.controller;

import com.zooSabana.demo.controller.dto.AnimalDTO;
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
        registroMedicoService.saveRegistroMedico(registroMedicoDTO.animal_id(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro médico guardado exitosamente");
    }

    @GetMapping(path = "/registros-medicos")
    public ResponseEntity<Object> getRegistrosMedicos() {
        return ResponseEntity.status(HttpStatus.OK).body(registroMedicoService.getRegistrosMedicos());
    }

    @GetMapping(path = "/registros-medicos/{id}")
    public ResponseEntity<Object> getRegistroMedicoById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(registroMedicoService.getRegistroMedico(id));
    }

    @GetMapping(path = "/registros-medicos/animales/{animal_id}")
    public ResponseEntity<Object> getRegistrosMedicosByAnimal(@PathVariable Long animal_id) {
        return ResponseEntity.status(HttpStatus.OK).body(registroMedicoService.getRegistrosMedicosByAnimal(animal_id));
    }

    @GetMapping(path = "/registros-medicos/animales/revision-pendiente-mes")
    public ResponseEntity<List<Map<String, Object>>> getAnimalesSinRevision() {
        List<Map<String, Object>> animalesSinRevision = registroMedicoService.getAnimalesSinRevision();
        return ResponseEntity.status(HttpStatus.OK).body(animalesSinRevision);
    }


    @PutMapping(path = "/registros-medicos/{id}")
    public ResponseEntity<String> updateRegistroMedico(@PathVariable Long id, @RequestBody RegistroMedicoDTO registroMedicoDTO) {
        registroMedicoService.updateRegistroMedico(id, registroMedicoDTO.animal_id(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento());
        return ResponseEntity.status(HttpStatus.OK).body("Registro médico actualizado exitosamente");
    }

    @DeleteMapping(path = "/registros-medicos/{id}")
    public ResponseEntity<String> deleteRegistroMedico(@PathVariable Long id) {
        registroMedicoService.deleteRegistroMedico(id);
        return ResponseEntity.status(HttpStatus.OK).body("Registro médico eliminado exitosamente");
    }
}
