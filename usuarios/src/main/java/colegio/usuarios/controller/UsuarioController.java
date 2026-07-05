package colegio.usuarios.controller;

import colegio.usuarios.dto.ApiResponse;
import colegio.usuarios.dto.RoleDTO;
import colegio.usuarios.dto.UsuarioCreateDTO;
import colegio.usuarios.dto.UsuarioCredentialsDTO;
import colegio.usuarios.dto.UsuarioDTO;
import colegio.usuarios.model.Usuario;
import colegio.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con un rol específico")
    public ResponseEntity<ApiResponse<UsuarioDTO>> register(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            Usuario newUsuario = usuarioService.registrarUsuario(
                    dto.getRut(), dto.getNombre(), dto.getApellido(), 
                    dto.getCorreo(), dto.getPassword(), dto.getRoleId(), dto.getCursoId()
            );

            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    newUsuario.getId(),
                    newUsuario.getRut(),
                    newUsuario.getNombre(),
                    newUsuario.getApellido(),
                    newUsuario.getCorreo(),
                    new RoleDTO(newUsuario.getRol().getIdrol(), newUsuario.getRol().getNombre()),
                    newUsuario.getCursoId()
            );

            ApiResponse<UsuarioDTO> response = new ApiResponse<>(200, "Usuario registrado correctamente", usuarioDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", e.getMessage());
            ApiResponse<UsuarioDTO> response = new ApiResponse<>(400, "Error al registrar usuario: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales del usuario y permite el acceso")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UsuarioCredentialsDTO dto) {
        try {
            String token = usuarioService.login(dto.getCorreo(), dto.getPassword());

            if (token != null) {
                ApiResponse<String> response = new ApiResponse<>(200, "Login exitoso", token);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = new ApiResponse<>(401, "Credenciales inválidas", null);
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            logger.error("Error al iniciar sesión: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(400, "Error al iniciar sesión: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> getAllUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.getAllUsuariosDTO();
            ApiResponse<List<UsuarioDTO>> response = new ApiResponse<>(200, "Listado de usuarios", usuarios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al listar usuarios: {}", e.getMessage());
            ApiResponse<List<UsuarioDTO>> response = new ApiResponse<>(500, "Error al listar usuarios: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token", description = "Permite validar un token de autenticación para verificar su validez.")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestParam String token) {
        try {
            boolean valid = usuarioService.validateToken(token);

            if (valid) {
                ApiResponse<String> response = new ApiResponse<>(200, "Token válido", "OK");
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            logger.error("Error al validar token: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(400, "Error al validar token: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}