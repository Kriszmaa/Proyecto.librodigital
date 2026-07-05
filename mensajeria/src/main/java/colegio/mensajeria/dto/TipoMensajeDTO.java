package colegio.mensajeria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TipoMensajeDTO {
    private Long idtipo;
    private String nombreTipo;
}