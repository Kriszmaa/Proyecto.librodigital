package colegio.usuarios.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import colegio.usuarios.model.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByRut(String rut);
    Optional<Usuario> findByCorreo(String correo);
}