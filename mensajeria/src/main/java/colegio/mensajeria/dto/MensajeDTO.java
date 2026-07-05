package colegio.mensajeria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensajeDTO {
    private Long id;
    private String asunto;
    private String contenido;
    private Long idRemitente;
    private Long idDestinatario;
    private TipoMensajeDTO tipo;
}