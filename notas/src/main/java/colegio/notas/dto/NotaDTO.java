package colegio.notas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotaDTO {
    private Long id;
    private Double valor;
    private Long idAlumno;
    private AsignaturaDTO asignatura;
}