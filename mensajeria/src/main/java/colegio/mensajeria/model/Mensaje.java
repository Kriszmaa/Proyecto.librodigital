package colegio.mensajeria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mensajes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "id_remitente", nullable = false)
    private Long idRemitente;

    @Column(name = "id_destinatario", nullable = false)
    private Long idDestinatario;

    @ManyToOne
    @JoinColumn(name = "idtipo_msg")
    private TipoMensaje tipoMensaje;
}