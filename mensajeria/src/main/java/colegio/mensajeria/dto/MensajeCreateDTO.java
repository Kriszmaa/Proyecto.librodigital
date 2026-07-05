package colegio.mensajeria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MensajeCreateDTO {

    @NotBlank(message = "El asunto no puede estar vacío")
    private String asunto;

    @NotBlank(message = "El contenido no puede estar vacío")
    private String contenido;

    @NotNull(message = "El ID del remitente es obligatorio")
    private Long idRemitente;

    @NotNull(message = "El ID del destinatario es obligatorio")
    private Long idDestinatario;

    @NotNull(message = "El ID del tipo de mensaje es obligatorio")
    private Long idTipo;
}