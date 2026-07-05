CREATE TABLE roles (
    idrol BIGINT NOT NULL AUTO_INCREMENT,
    nombreRol VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (idrol)
);

CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rut VARCHAR(12) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    idrol_user BIGINT,
    curso_id VARCHAR(30),
    PRIMARY KEY (id),
    FOREIGN KEY (idrol_user) REFERENCES roles(idrol)
);