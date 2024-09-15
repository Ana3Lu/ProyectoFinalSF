package com.zooSabana.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class EspecieController {

    private EspecieJPA especieJPA;

    List<EspecieDTO> especies = new ArrayList<>();

    public EspecieController() {
        especies.add(new EspecieDTO(1L, "Mamiferos"));
        especies.add(new EspecieDTO(2L, "Aves"));
    }

    @GetMapping(path = "/especies")
    public List<EspecieDTO> getEspecies(@RequestParam Long id) {
        return especies
                .stream()
                .filter(animalDTO -> animalDTO.id().equals(id))
                .toList();
    }

    @GetMapping(path = "/especies/{id}")
    public EspecieDTO getAnimalById(@PathVariable Long id) {
        for (EspecieDTO especie : especies) {
            if (especie.id().equals(id)) {
                return especie;
            }
        }
        return null;
    }

    @GetMapping(path = "/especies-bd")
    public List<EspecieORM> getEspeciesBD() {
        return especieJPA.findAll();
    }

    @PostMapping(path = "/especie")
    public String createAnimal(@RequestBody EspecieDTO animalDTO) {
        especies.add(animalDTO);
        especieJPA.save(new EspecieORM(animalDTO.id(), animalDTO.nombre()));
        return "Especie guardada";
    }

    @DeleteMapping(path = "/especie/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        for (EspecieDTO especie : especies) {
            if (especie.id().equals(id)) {
                especies.remove(especie);
                return "Especie eliminada";
            }
        }
        return "Especie no encontrada";
    }
}
