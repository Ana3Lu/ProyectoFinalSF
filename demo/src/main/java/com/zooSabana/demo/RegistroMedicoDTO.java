package com.zooSabana.demo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.YearMonth;

public record RegistroMedicoDTO(Long id, Long animalId, @JsonFormat(pattern = "yyyy-MM") YearMonth fecha, String estado, String dieta, String comportamiento) {
}
