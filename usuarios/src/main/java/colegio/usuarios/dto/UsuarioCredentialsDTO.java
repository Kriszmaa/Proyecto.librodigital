package colegio.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioCredentialsDTO {

    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}