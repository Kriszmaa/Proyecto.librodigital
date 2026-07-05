package colegio.mensajeria.service;

import colegio.mensajeria.dto.MensajeDTO;
import colegio.mensajeria.dto.TipoMensajeDTO;
import colegio.mensajeria.dto.ApiResponse;
import colegio.mensajeria.model.Mensaje;
import colegio.mensajeria.model.TipoMensaje;
import colegio.mensajeria.repository.RepositoryMensaje;
import colegio.mensajeria.repository.TipoMensajeRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final RepositoryMensaje repositoryMensaje;
    private final TipoMensajeRepository tipoMensajeRepository;
    private final WebClient.Builder webClientBuilder;

    private boolean verificarUsuarioExiste(Long idUsuario) {
        try {
            // Llamamos al endpoint de validación lógica del ms-usuarios registrado en Eureka
            Boolean existe = webClientBuilder.build()
                    .get()
                    .uri("http://usuarios/api/v1/usuarios/list")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {})
                    .map(response -> response != null && response.getCode() == 200)
                    .block();
            return Boolean.TRUE.equals(existe);
        } catch (Exception e) {
            throw new IllegalStateException("Error de comunicación al validar usuario: " + e.getMessage());
        }
    }

    public Mensaje enviarMensaje(String asunto, String contenido, Long idRemitente, Long idDestinatario, Long idTipo) {
        if (!verificarUsuarioExiste(idRemitente)) {
            throw new IllegalArgumentException("El remitente con ID " + idRemitente + " no existe.");
        }
        if (!verificarUsuarioExiste(idDestinatario)) {
            throw new IllegalArgumentException("El destinatario con ID " + idDestinatario + " no existe.");
        }

        TipoMensaje tipo = tipoMensajeRepository.findById(idTipo)
                .orElseThrow(() -> new IllegalArgumentException("El tipo de mensaje especificado no existe."));

        Mensaje mensaje = new Mensaje(null, asunto, contenido, idRemitente, idDestinatario, tipo);
        return repositoryMensaje.save(mensaje);
    }

    public List<MensajeDTO> getMensajesPorDestinatario(Long idDestinatario) {
        return repositoryMensaje.findByIdDestinatario(idDestinatario)
                .stream()
                .map(m -> new MensajeDTO(
                        m.getId(), m.getAsunto(), m.getContenido(), m.getIdRemitente(), m.getIdDestinatario(),
                        new TipoMensajeDTO(m.getTipoMensaje().getIdtipo(), m.getTipoMensaje().getNombre())
                )).toList();
    }
    public void eliminarMensaje(Long id) {
        if (!repositoryMensaje.existsById(id)) {
            throw new IllegalArgumentException("El mensaje con ID " + id + " no existe.");
        }
        repositoryMensaje.deleteById(id);
    }
}