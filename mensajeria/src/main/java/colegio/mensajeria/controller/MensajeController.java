package colegio.mensajeria.controller;

import colegio.mensajeria.service.MensajeService;
import colegio.mensajeria.dto.ApiResponse;
import colegio.mensajeria.dto.MensajeCreateDTO;
import colegio.mensajeria.dto.MensajeDTO;
import colegio.mensajeria.dto.TipoMensajeDTO;
import colegio.mensajeria.model.Mensaje;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mensajeria")
public class MensajeController {

    private final MensajeService mensajeService;

    @PostMapping("/send")
    @Operation(summary = "Enviar un mensaje masivo o directo", description = "Crea un nuevo mensaje validando remitente y destinatario con el ms-usuarios")
    public ResponseEntity<ApiResponse<MensajeDTO>> enviar(@Valid @RequestBody MensajeCreateDTO dto) {
        try {
            Mensaje m = mensajeService.enviarMensaje(
                    dto.getAsunto(), dto.getContenido(), dto.getIdRemitente(), dto.getIdDestinatario(), dto.getIdTipo()
            );

            MensajeDTO responseDto = new MensajeDTO(
                    m.getId(), m.getAsunto(), m.getContenido(), m.getIdRemitente(), m.getIdDestinatario(),
                    new TipoMensajeDTO(m.getTipoMensaje().getIdtipo(), m.getTipoMensaje().getNombre())
            );

            return ResponseEntity.ok(new ApiResponse<>(200, "Mensaje enviado exitosamente", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @GetMapping("/recibidos/{id}")
    @Operation(summary = "Listar mensajes recibidos", description = "Obtiene la bandeja de entrada de un usuario")
    public ResponseEntity<ApiResponse<List<MensajeDTO>>> getRecibidos(@PathVariable Long id) {
        try {
            List<MensajeDTO> lista = mensajeService.getMensajesPorDestinatario(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Bandeja de entrada", lista));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(500, e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar un mensaje por ID", description = "Elimina físicamente un mensaje de la base de datos utilizando su identificador")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        try {
            mensajeService.eliminarMensaje(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Mensaje eliminado exitosamente", "ID eliminado: " + id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }
}