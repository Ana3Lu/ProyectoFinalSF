package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.RegistroMedicoJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RegistroMedicoService {

    private RegistroMedicoJPA registroMedicoJPA;

    private AnimalJPA animalJPA;

    public void saveRegistroMedico(Long animal_id, LocalDate fecha, String estado, String dieta, String comportamiento) {
        if (animal_id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)) {
            throw new IllegalArgumentException("Fecha de registro médico inválida");
        }
        if (estado == null || estado.isBlank() || dieta == null || dieta.isBlank() || comportamiento == null || comportamiento.isBlank()) {
            throw new IllegalArgumentException("Ningún campo del registro médico del animal puede estar vacio");
        }
        AnimalORM animal = animalJPA.findById(animal_id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        RegistroMedicoORM nuevoRegistroMedico = new RegistroMedicoORM();
        nuevoRegistroMedico.setAnimal(animal);
        nuevoRegistroMedico.setFecha(fecha);
        nuevoRegistroMedico.setEstado(estado);
        nuevoRegistroMedico.setDieta(dieta);
        nuevoRegistroMedico.setComportamiento(comportamiento);
        registroMedicoJPA.save(nuevoRegistroMedico);
    }

    public List<RegistroMedicoORM> getRegistrosMedicos() {
        return registroMedicoJPA.findAll();
    }

    public RegistroMedicoORM getRegistroMedico(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        return registroMedicoJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro médico no encontrado"));
    }

    public List<RegistroMedicoORM> getRegistrosMedicosByAnimal(Long animal_id) {
        if (animal_id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        List<RegistroMedicoORM> registrosMedicos = registroMedicoJPA.findByAnimal_Id(animal_id);
        if (registrosMedicos.isEmpty()) {
            throw new NoSuchElementException("Animal no encontrado");
        }
        return registrosMedicos;
    }

    public List<Map<String, Object>> getAnimalesSinRevision() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate inicioMes = fechaActual.withDayOfMonth(1);

        List<Long> animalesConControl = registroMedicoJPA.findDistinctAnimalIdsByFechaBetween(inicioMes, fechaActual);
        List<AnimalORM> allAnimales = animalJPA.findAll();

        return allAnimales.stream()
                .filter(animal -> !animalesConControl.contains(animal.getId()))
                .map(animal -> {
                    LocalDate ultimaFechaRevision = registroMedicoJPA.findUltimaFechaByAnimalId(animal.getId());

                    Map<String, Object> datosAnimal = new HashMap<>();
                    datosAnimal.put("id", animal.getId());
                    datosAnimal.put("nombre", animal.getNombre());
                    datosAnimal.put("especie", animal.getEspecie().getNombre());
                    datosAnimal.put("ultimaFechaRevision", ultimaFechaRevision);
                    datosAnimal.put("cuidador", animal.getEspecie().getCuidador().getNombre());
                    datosAnimal.put("emailCuidador", animal.getEspecie().getCuidador().getEmail());
                    return datosAnimal;
                })
                .toList();
    }



    public void updateRegistroMedico(Long id, Long animal_id, LocalDate fecha, String estado, String dieta, String comportamiento) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        if (animal_id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)) {
            throw new IllegalArgumentException("Fecha de registro médico inválida");
        }
        if (estado == null || estado.isBlank() || dieta == null || dieta.isBlank() || comportamiento == null || comportamiento.isBlank()) {
            throw new IllegalArgumentException("Ningún campo del registro médico del animal puede estar vacio");
        }
        AnimalORM animal = animalJPA.findById(animal_id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        RegistroMedicoORM registroMedico = registroMedicoJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro médico no encontrado"));
        registroMedico.setAnimal(animal);
        registroMedico.setFecha(fecha);
        registroMedico.setEstado(estado);
        registroMedico.setDieta(dieta);
        registroMedico.setComportamiento(comportamiento);
        registroMedicoJPA.save(registroMedico);
    }

    public void deleteRegistroMedico(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        if (!registroMedicoJPA.existsById(id)) {
            throw new NoSuchElementException("Registro médico no encontrado");
        }
        registroMedicoJPA.deleteById(id);
    }
}
