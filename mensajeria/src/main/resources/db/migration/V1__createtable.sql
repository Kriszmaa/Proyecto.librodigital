CREATE TABLE tipos_mensaje (
    idtipo BIGINT NOT NULL AUTO_INCREMENT,
    nombreTipo VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (idtipo)
);
CREATE TABLE mensajes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    asunto VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL,
    id_remitente BIGINT NOT NULL,   
    id_destinatario BIGINT NOT NULL, -
    idtipo_msg BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (idtipo_msg) REFERENCES tipos_mensaje(idtipo)

INSERT INTO tipos_mensaje (nombreTipo) VALUES ('CIRCULAR');
INSERT INTO tipos_mensaje (nombreTipo) VALUES ('ALERTA');
INSERT INTO tipos_mensaje (nombreTipo) VALUES ('DIRECTO');