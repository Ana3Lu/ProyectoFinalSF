package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.EspecieORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EspecieService {

    private final EspecieJPA especieJPA;


    public void saveEspecie(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de especie inválido");
        }
        EspecieORM newEspecie = new EspecieORM();
        newEspecie.setNombre(nombre);
        especieJPA.save(newEspecie);
    }

    public List<EspecieORM> getEspecies() {
        return especieJPA.findAll();
    }

    public EspecieORM getEspecieById(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        return especieJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
    }

    public void updateEspecie(Long id, String nombre) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        EspecieORM especie = especieJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        especie.setNombre(nombre);
        especieJPA.save(especie);
    }

    public void deleteEspecie(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (!especieJPA.existsById(id)) {
            throw new NoSuchElementException("Especie no encontrada");
        }
        especieJPA.deleteById(id);
    }
}
