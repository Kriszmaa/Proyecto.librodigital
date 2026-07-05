package colegio.notas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsignaturaDTO {
    private Long idasignatura;
    private String nombreAsignatura;
}