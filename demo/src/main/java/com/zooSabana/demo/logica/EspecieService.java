package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.jpa.EspecieJPA;
import com.zooSabana.demo.db.orm.CuidadorORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EspecieService {

    private final CuidadorJPA cuidadorJPA;
    private final EspecieJPA especieJPA;

    public void saveEspecie(Long cuidador_id, String nombre) {
        if (cuidador_id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de especie inválido");
        }
        CuidadorORM cuidador = cuidadorJPA.findById(cuidador_id)
                .orElseThrow(() -> new NoSuchElementException("Cuidador no encontrado"));
        EspecieORM nuevaEspecie = new EspecieORM();
        nuevaEspecie.setNombre(nombre);
        nuevaEspecie.setCuidador(cuidador);
        especieJPA.save(nuevaEspecie);
    }

    public List<EspecieORM> getEspecies() {
        return especieJPA.findAll();
    }

    public List<EspecieORM> getEspeciesByCuidador(Long cuidador_id) {
        if (cuidador_id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        List<EspecieORM> especies = especieJPA.findByCuidador_Id(cuidador_id);
        return especies;
    }

    public EspecieORM getEspecie(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        return especieJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
    }

    public void updateEspecie(Long id, long cuidador_id, String nombre) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de especie inválido");
        }
        if (cuidador_id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de especie inválido");
        }
        CuidadorORM cuidador = cuidadorJPA.findById(cuidador_id)
                .orElseThrow(() -> new NoSuchElementException("Cuidador no encontrado"));
        EspecieORM especie = especieJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Especie no encontrada"));
        especie.setCuidador(cuidador);
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
