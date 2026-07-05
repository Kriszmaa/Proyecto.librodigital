package colegio.mensajeria.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_mensaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtipo;

    @Column(name = "nombreTipo", nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "tipoMensaje", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Mensaje> mensajes = new ArrayList<>();
}