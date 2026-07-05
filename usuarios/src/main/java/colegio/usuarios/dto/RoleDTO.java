package colegio.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDTO {
    private Long idrol;
    private String nombreRol;
}