package colegio.notas.service;

import colegio.notas.dto.NotaDTO;
import colegio.notas.dto.AsignaturaDTO;
import colegio.notas.dto.ApiResponse;
import colegio.notas.model.Nota;
import colegio.notas.model.Asignatura;
import colegio.notas.repository.RepositoryNota;
import colegio.notas.repository.AsignaturaRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final RepositoryNota repositoryNota;
    private final AsignaturaRepository asignaturaRepository;
    private final WebClient.Builder webClientBuilder;

    private boolean verificarAlumnoExiste(Long idAlumno) {
        try {
            Boolean existe = webClientBuilder.build()
                    .get()
                    .uri("http://usuarios/api/v1/usuarios/list")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<Object>>>() {})
                    .map(response -> response != null && response.getCode() == 200)
                    .block();
            return Boolean.TRUE.equals(existe);
        } catch (Exception e) {
            throw new IllegalStateException("Error al conectar con ms-usuarios: " + e.getMessage());
        }
    }

    public Nota registrarNota(Double valor, Long idAlumno, Long idAsignatura) {
        if (valor < 1.0 || valor > 7.0) {
            throw new IllegalArgumentException("La nota debe estar en el rango de 1.0 a 7.0");
        }

        if (!verificarAlumnoExiste(idAlumno)) {
            throw new IllegalArgumentException("El alumno con ID " + idAlumno + " no existe.");
        }

        Asignatura asignatura = asignaturaRepository.findById(idAsignatura)
                .orElseThrow(() -> new IllegalArgumentException("La asignatura especificada no existe."));

        Nota nota = new Nota(null, valor, idAlumno, asignatura);
        return repositoryNota.save(nota);
    }

    public List<NotaDTO> getNotasPorAlumno(Long idAlumno) {
        return repositoryNota.findByIdAlumno(idAlumno)
                .stream()
                .map(n -> new NotaDTO(
                        n.getId(), n.getValor(), n.getIdAlumno(),
                        new AsignaturaDTO(n.getAsignatura().getIdasignatura(), n.getAsignatura().getNombre())
                )).toList();
    }
}