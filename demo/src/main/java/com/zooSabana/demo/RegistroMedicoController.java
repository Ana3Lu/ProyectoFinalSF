package com.zooSabana.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class RegistroMedicoController {

    private RegistroMedicoJPA registroMedicoJPA;

    List<RegistroMedicoDTO> registros = new ArrayList<>();

    public RegistroMedicoController() {
        registros.add(new RegistroMedicoDTO(1L, 1L, 202201, "Enfermo", "Vegetariana", "Estable"));
        registros.add(new RegistroMedicoDTO(2L, 2L, 202202, "Sano", "Carnivora", "Estable"));
    }

    @GetMapping(path = "/registros-medicos/animal/{animalId}")
    public List<RegistroMedicoDTO> getRegistrosMedicosByAnimal(@PathVariable Long animalId) {
        return registros
                .stream()
                .filter(registroMedicoDTO -> registroMedicoDTO.animalId().equals(animalId))
                .toList();
    }

    @GetMapping(path = "/registros-medicos")
    public List<Long> getRegistrosMedicosByFecha(@RequestParam int fecha) {
        List<Long> animalesConControl = registros
                .stream()
                .filter(registro -> registro.fecha() == fecha)
                .map(RegistroMedicoDTO::animalId)
                .distinct()
                .toList();
        List<Long> animalesTodos = registros
                .stream()
                .map(RegistroMedicoDTO::animalId)
                .distinct()
                .toList();
        return animalesTodos
                .stream()
                .filter(animalId -> !animalesConControl.contains(animalId))
                .toList();
    }

    @GetMapping(path = "/registros-medicos/{id}")
    public RegistroMedicoDTO getRegistroMedicoById(@PathVariable Long id) {
        for (RegistroMedicoDTO registroMedicoDTO : registros) {
            if (registroMedicoDTO.id().equals(id)) {
                return registroMedicoDTO;
            }
        }
        return null;
    }

    @GetMapping(path = "/registros-medicos-bd")
    public List<RegistroMedicoORM> getRegistrosMedicosBD() {
        return registroMedicoJPA.findAll();
    }

    @PostMapping(path = "/registro-medico")
    public String createRegistroMedico(@RequestBody RegistroMedicoDTO registroMedicoDTO) {
        registros.add(registroMedicoDTO);
        registroMedicoJPA.save(new RegistroMedicoORM(registroMedicoDTO.id(), registroMedicoDTO.animalId(), registroMedicoDTO.fecha(), registroMedicoDTO.estado(), registroMedicoDTO.dieta(), registroMedicoDTO.comportamiento()));
        return "Registro medico guardado";
    }

    @DeleteMapping(path = "/registro-medico/{id}")
    public String deleteRegistroMedico(@PathVariable Long id) {
        for (RegistroMedicoDTO registroMedicoDTO : registros) {
            if (registroMedicoDTO.id().equals(id)) {
                registros.remove(registroMedicoDTO);
                return "Registro medico eliminado";
            }
        }
        return "Registro medico no encontrado";
    }

}
