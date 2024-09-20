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
        EspecieORM nuevoEspecie = new EspecieORM();
        nuevoEspecie.setNombre(nombre);
        especieJPA.save(nuevoEspecie);
    }

    public List<EspecieORM> getEspecie() {
        return especieJPA.findAll();
    }

    public EspecieORM getEspecieById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
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

    public void deleteEspecie(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de animal inválido");
        }
        if (!especieJPA.existsById(id)) {
            throw new NoSuchElementException("Animal no encontrado");
        }
        especieJPA.deleteById(id);
    }
}
