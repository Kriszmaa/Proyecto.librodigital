package colegio.notas.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idasignatura;

    @Column(name = "nombreAsignatura", nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Nota> notas = new ArrayList<>();
}