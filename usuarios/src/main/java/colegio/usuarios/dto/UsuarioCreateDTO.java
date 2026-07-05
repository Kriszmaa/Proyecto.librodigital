package colegio.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioCreateDTO {

    @NotBlank(message = "El RUT no puede estar vacío")
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El id del rol es obligatorio")
    private Long roleId;

    private String cursoId;
}