package colegio.usuarios.service;

import colegio.usuarios.dto.RoleDTO;
import colegio.usuarios.dto.UsuarioDTO;
import colegio.usuarios.model.Rol;
import colegio.usuarios.model.Usuario;
import colegio.usuarios.repository.RolRepository;
import colegio.usuarios.repository.RepositoryUsuario;
import colegio.usuarios.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final RepositoryUsuario repositoryUsuario;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public Usuario registrarUsuario(String rut, String nombre, String apellido, String correo, String rawPassword, Long roleId, String cursoId) {
        if (repositoryUsuario.findByRut(rut).isPresent()) {
            throw new IllegalArgumentException("El usuario con este RUT: " + rut + " ya existe actualmente");
        }
        if (repositoryUsuario.findByCorreo(correo).isPresent()) {
            throw new IllegalArgumentException("El correo: " + correo + " ya está registrado");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        Rol databaseRol = rolRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("El rol especificado no existe"));

        Usuario usuario = new Usuario(null, rut, nombre, apellido, correo, encodedPassword, databaseRol, cursoId);
        return repositoryUsuario.save(usuario);
    }

    public String login(String correo, String rawPassword) {
        Optional<Usuario> usuarioOpt = repositoryUsuario.findByCorreo(correo);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(rawPassword, usuarioOpt.get().getPassword())) {
            return jwtUtil.generateToken(correo);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public List<UsuarioDTO> getAllUsuariosDTO() {
        return repositoryUsuario.findAll()
                .stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getRut(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getCorreo(),
                        new RoleDTO(u.getRol().getIdrol(), u.getRol().getNombre()),
                        u.getCursoId()
                ))
                .toList();
    }
}