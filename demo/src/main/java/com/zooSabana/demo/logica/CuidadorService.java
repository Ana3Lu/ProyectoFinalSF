package com.zooSabana.demo.logica;

import com.zooSabana.demo.db.jpa.CuidadorJPA;
import com.zooSabana.demo.db.orm.AnimalORM;
import com.zooSabana.demo.db.orm.CuidadorORM;
import com.zooSabana.demo.db.orm.EspecieORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CuidadorService {

    private final CuidadorJPA cuidadorJPA;


    public void saveCuidador(String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de cuidador inválido");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email de cuidador inválido");
        }
        CuidadorORM nuevoCuidador = new CuidadorORM();
        nuevoCuidador.setNombre(nombre);
        nuevoCuidador.setEmail(email);
        cuidadorJPA.save(nuevoCuidador);
    }

    public List<CuidadorORM> getCuidadores() {
        return cuidadorJPA.findAll();
    }

    public CuidadorORM getCuidador(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        return cuidadorJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuidador no encontrado"));
    }

    public void updateCuidador(Long id, String nombre, String email) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de cuidador inválido");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email de cuidador inválido");
        }
        CuidadorORM cuidador = cuidadorJPA.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuidador no encontrado"));
        cuidador.setNombre(nombre);
        cuidador.setEmail(email);
        cuidadorJPA.save(cuidador);
    }

    public void deleteCuidador(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de cuidador inválido");
        }
        if (!cuidadorJPA.existsById(id)) {
            throw new NoSuchElementException("Cuidador no encontrado");
        }
        cuidadorJPA.deleteById(id);
    }
}
