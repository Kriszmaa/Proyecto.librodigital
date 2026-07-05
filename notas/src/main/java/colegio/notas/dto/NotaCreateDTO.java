package colegio.notas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotaCreateDTO {

    @NotNull(message = "El valor de la nota es obligatorio")
    @Min(value = 1, message = "La nota mínima es 1.0")
    @Max(value = 7, message = "La nota máxima es 7.0")
    private Double valor;

    @NotNull(message = "El ID del alumno es obligatorio")
    private Long idAlumno;

    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Long idAsignatura;
}