DROP TABLE pacientes IF EXISTS;
DROP TABLE domicilios IF EXISTS;

CREATE TABLE domicilios(
    id int PRIMARY KEY AUTO_INCREMENT,
    calle varchar(25) NOT NULL,
    numero int NOT NULL,
    localidad varchar(25) NOT NULL,
    provincia varchar(25) NOT NULL
);

CREATE TABLE pacientes(
    id int PRIMARY KEY AUTO_INCREMENT,
    apellido varchar(25) NOT NULL,
    nombre varchar(25) NOT NULL,
    dni varchar(15) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    id_domicilio int NOT NULL
);

INSERT INTO domicilios VALUES
(DEFAULT, 'Av.americas', 23, 'Garzota', 'Guayas'),
(DEFAULT, 'via a la costa', 34, 'Tarqui', 'Guayas'),
(DEFAULT, 'Av.quito', 57, 'Los gallos', 'Pichincha');

INSERT INTO pacientes VALUES
(DEFAULT, 'Sauhing', 'Axel', '0956783212', '2025-02-20', 1),
(DEFAULT, 'Rivera', 'Alejandro', '0916582211', '2025-02-17', 2),
(DEFAULT, 'Aguilar', 'Alan', '0998233318', '2025-02-21', 3);

