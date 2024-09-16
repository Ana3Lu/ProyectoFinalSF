package com.zooSabana.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.YearMonth;

public record RegistroMedicoDTO(Long id, Long animalId, @JsonFormat(pattern = "yyyy-MM") YearMonth fecha, String estado, String dieta, String comportamiento) {
}
