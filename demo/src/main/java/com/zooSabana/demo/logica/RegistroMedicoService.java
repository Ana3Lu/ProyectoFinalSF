package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.AnimalJPA;
import com.zooSabana.demo.db.jpa.RegistroMedicoJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.RegistroMedicoORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RegistroMedicoService {

    private RegistroMedicoJPA registroMedicoJPA;

    private AnimalJPA animalJPA;


    public void saveRegistroMedico(Long animalId, LocalDate fecha, String estado, String dieta, String comportamiento) {
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)) {
            throw new IllegalArgumentException("Fecha de registro médico inválida");
        }
        if (animalId < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        AnimalORM animal = animalJPA.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        RegistroMedicoORM nuevoRegistroMedico = new RegistroMedicoORM();
        nuevoRegistroMedico.setAnimal(animal);
        nuevoRegistroMedico.setFecha(fecha);
        nuevoRegistroMedico.setEstado(estado);
        nuevoRegistroMedico.setDieta(dieta);
        nuevoRegistroMedico.setComportamiento(comportamiento);
        registroMedicoJPA.save(nuevoRegistroMedico);
    }

    public List<RegistroMedicoORM> getAllRegistrosMedicos() {
        return registroMedicoJPA.findAll();
    }

    public List<RegistroMedicoORM> getRegistrosMedicos(Long animalId) {
        if (animalId < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        AnimalORM animal = animalJPA.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        return registroMedicoJPA.findAll()
                .stream()
                .filter(registroMedico -> animal.equals(registroMedico.getAnimal()))
                .toList();
    }

    public List<Long> getAnimalesSinRevision(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)) {
            throw new IllegalArgumentException("Fecha de registro médico inválida");
        }
        List<Long> animalesConControl = registroMedicoJPA.findAll()
                .stream()
                .filter(registroMedico -> {
                    LocalDate registroFecha = registroMedico.getFecha();
                    return registroFecha.getYear() == fecha.getYear() &&
                            registroFecha.getMonth() == fecha.getMonth();
                })
                .map(registroMedico -> registroMedico.getAnimal().getId())
                .distinct()
                .toList();
        List<Long> allAnimales = animalJPA.findAll()
                .stream()
                .map(AnimalORM::getId)
                .toList();
        return allAnimales
                .stream()
                .filter(animalId -> !animalesConControl.contains(animalId))
                .toList();
    }


    public RegistroMedicoORM getRegistroMedico(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        return registroMedicoJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro médico no encontrado"));
    }

    public void updateRegistroMedico(Long id, Long animalId, LocalDate fecha, String estado, String dieta, String comportamiento) {
        if (animalId < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isAfter(fechaActual)) {
            throw new IllegalArgumentException("Fecha de registro médico inválida");
        }
        AnimalORM animal = animalJPA.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        RegistroMedicoORM registroMedico = registroMedicoJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro médico no encontrada"));
        registroMedico.setAnimal(animal);
        registroMedico.setFecha(fecha);
        registroMedico.setEstado(estado);
        registroMedico.setDieta(dieta);
        registroMedico.setComportamiento(comportamiento);
        registroMedicoJPA.save(registroMedico);
    }

    public void deleteRegistroMedico(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de registro médico inválido");
        }
        if (!registroMedicoJPA.existsById(id)) {
            throw new NoSuchElementException("Animal no encontrado");
        }
        registroMedicoJPA.deleteById(id);
    }
}
