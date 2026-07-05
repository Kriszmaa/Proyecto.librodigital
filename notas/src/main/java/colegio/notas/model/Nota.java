package colegio.notas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "id_alumno", nullable = false)
    private Long idAlumno;

    @ManyToOne
    @JoinColumn(name = "idasignatura_nota")
    private Asignatura asignatura;
}