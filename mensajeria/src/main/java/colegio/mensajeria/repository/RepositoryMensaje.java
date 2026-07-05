package colegio.mensajeria.repository;

import colegio.mensajeria.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepositoryMensaje extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByIdDestinatario(Long idDestinatario);
    List<Mensaje> findByIdRemitente(Long idRemitente);
}