package colegio.mensajeria.repository;

import colegio.mensajeria.model.TipoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TipoMensajeRepository extends JpaRepository<TipoMensaje, Long> {
}