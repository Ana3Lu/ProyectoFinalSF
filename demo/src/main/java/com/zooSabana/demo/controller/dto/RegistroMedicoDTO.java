package com.zooSabana.demo.controller.dto;

import java.time.LocalDate;

public record RegistroMedicoDTO(Long animal_id, LocalDate fecha, String estado, String dieta, String comportamiento) {
}
