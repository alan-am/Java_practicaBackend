--archivo de inicializacion de datos en la tablas de la db
INSERT INTO domicilios (calle, numero, localidad, provincia)
VALUES
    ('Av. Siempre Viva', 742, 'Springfield', 'Illinois'),
    ('Calle Falsa', 123, 'Ciudad Gótica', 'Nueva York'),
    ('Av. del Sol', 456, 'Los Ángeles', 'California');

INSERT INTO pacientes (apellido, nombre, dni, fecha_ingreso, domicilio_id)
VALUES
    ('Simpson', 'Homer', '12345678', '2024-03-05', 1),
    ('Wayne', 'Bruce', '87654321', '2024-03-04', 2),
    ('Smith', 'John', '11223344', '2024-03-03', 3);