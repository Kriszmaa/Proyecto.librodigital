package colegio.notas.controller;

import colegio.notas.service.NotaService;
import colegio.notas.dto.ApiResponse;
import colegio.notas.dto.NotaCreateDTO;
import colegio.notas.dto.NotaDTO;
import colegio.notas.dto.AsignaturaDTO;
import colegio.notas.model.Nota;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notas")
public class NotaController {

    private final NotaService notaService;

    @PostMapping("/register")
    @Operation(summary = "Registrar una nueva nota", description = "Guarda la nota validando previamente la existencia del alumno en ms-usuarios")
    public ResponseEntity<ApiResponse<NotaDTO>> registrar(@Valid @RequestBody NotaCreateDTO dto) {
        try {
            Nota n = notaService.registrarNota(dto.getValor(), dto.getIdAlumno(), dto.getIdAsignatura());
            NotaDTO responseDto = new NotaDTO(
                    n.getId(), n.getValor(), n.getIdAlumno(),
                    new AsignaturaDTO(n.getAsignatura().getIdasignatura(), n.getAsignatura().getNombre())
            );
            return ResponseEntity.ok(new ApiResponse<>(200, "Nota registrada con éxito", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/alumno/{idAlumno}")
    @Operation(summary = "Obtener notas de un alumno", description = "Retorna el listado de calificaciones de un estudiante específico")
    public ResponseEntity<ApiResponse<List<NotaDTO>>> getNotasAlumno(@PathVariable Long idAlumno) {
        try {
            List<NotaDTO> notas = notaService.getNotasPorAlumno(idAlumno);
            return ResponseEntity.ok(new ApiResponse<>(200, "Notas del alumno obtenidas", notas));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(500, e.getMessage(), null));
        }
    }
}