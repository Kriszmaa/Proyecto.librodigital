package colegio.notas.repository;

import colegio.notas.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryNota extends JpaRepository<Nota, Long> {
    List<Nota> findByIdAlumno(Long idAlumno);
}