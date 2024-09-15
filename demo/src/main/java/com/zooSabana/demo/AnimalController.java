package com.zooSabana.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class AnimalController {

    List<AnimalDTO> animales = new ArrayList<>();

    public AnimalController() {
        animales.add(new AnimalDTO(1L, 1L, "Tigre", 5));
        animales.add(new AnimalDTO(2L, 1L, "Jirafa", 2));
    }

    @GetMapping(path = "/animales")
    public List<AnimalDTO> getAnimales(@RequestParam Long especieId) {
        return animales
                .stream()
                .filter(animalDTO -> animalDTO.especieId().equals(especieId))
                .toList();
    }

    @GetMapping(path = "/animales/{id}")
    public AnimalDTO getAnimalById(@PathVariable Long id) {
        for (AnimalDTO animal : animales) {
            if (animal.id().equals(id)) {
                return animal;
            }
        }
        return null;
    }

    @PostMapping(path = "/animal")
    public String createAnimal(@RequestBody AnimalDTO animalDTO) {
        animales.add(animalDTO);
        return "Animal guardado";
    }

    @DeleteMapping(path = "/animal/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        for (AnimalDTO animal : animales) {
            if (animal.id().equals(id)) {
                animales.remove(animal);
                return "Animal eliminado";
            }
        }
        return "Animal no encontrado";
    }
}
