CREATE TABLE asignaturas (
    idasignatura BIGINT NOT NULL AUTO_INCREMENT,
    nombreAsignatura VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (idasignatura)
);

CREATE TABLE notas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    valor DECIMAL(2,1) NOT NULL,
    id_alumno BIGINT NOT NULL,
    idasignatura_nota BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (idasignatura_nota) REFERENCES asignaturas(idasignatura)
);

INSERT INTO asignaturas (nombreAsignatura) VALUES ('Matemáticas'), ('Lenguaje'), ('Historia'), ('Ciencias');