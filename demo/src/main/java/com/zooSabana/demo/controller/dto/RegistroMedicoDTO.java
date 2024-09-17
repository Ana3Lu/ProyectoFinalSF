package com.zooSabana.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record RegistroMedicoDTO(Long id, Long animalId, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM") LocalDate fecha, String estado, String dieta, String comportamiento) {
}
