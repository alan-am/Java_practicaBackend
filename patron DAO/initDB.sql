DROP TABLE medicamentos IF EXISTS;
CREATE TABLE medicamentos(
    id int PRIMARY KEY AUTO_INCREMENT,
    codigo int NOT NULL,
    nombre varchar(50) NOT NULL,
    laboratorio varchar(25) NOT NULL,
    cantidad int NOT NULL,
    precio decimal(7,2)
);

INSERT INTO medicamentos(codigo, nombre, laboratorio, cantidad, precio) VALUES
(5872, 'Paracetamol', 'Bayer', 24, 0.230),
(2345, 'Ibuprofeno', 'Pfizer', 67, 0.503),
(2347, 'Ibuprofeno', 'Wakanda', 153, 0.679);
