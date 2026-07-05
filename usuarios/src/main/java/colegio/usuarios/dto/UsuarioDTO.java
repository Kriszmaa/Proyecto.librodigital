package colegio.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private RoleDTO asig_rol;
    private String cursoId;
}