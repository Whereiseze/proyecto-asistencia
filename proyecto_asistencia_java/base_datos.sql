-- 1. Crear y seleccionar la base de datos
DROP DATABASE IF EXISTS asistencia_db;
CREATE DATABASE asistencia_db CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE asistencia_db;

-- 2. Tabla de Usuarios
CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          rut VARCHAR(20) NOT NULL UNIQUE,
                          nombre VARCHAR(100) NOT NULL,
                          correo VARCHAR(100) NOT NULL UNIQUE,
                          contrasena VARCHAR(100) NOT NULL,
                          rol ENUM('ADMIN', 'TRABAJADOR') NOT NULL DEFAULT 'TRABAJADOR'
) ENGINE=InnoDB;

-- 3. Tabla de Asistencia
CREATE TABLE asistencia (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            usuario_id INT NOT NULL,
                            fecha DATE NOT NULL,
                            hora_entrada TIME NOT NULL,
                            hora_salida TIME NULL,
                            FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4. Insertar datos iniciales de prueba

-- Usuario Administrador
INSERT INTO usuarios (rut, nombre, correo, contrasena, rol)
VALUES ('11111111-1', 'Administrador', 'admin@empresa.cl', 'admin123', 'ADMIN');

-- Usuarios Trabajadores
INSERT INTO usuarios (rut, nombre, correo, contrasena, rol)
VALUES ('22222222-2', 'Juan Perez', 'juan@empresa.cl', 'user123', 'TRABAJADOR');

INSERT INTO usuarios (rut, nombre, correo, contrasena, rol)
VALUES ('33333333-3', 'Maria Gonzalez', 'maria@empresa.cl', 'user123', 'TRABAJADOR');

-- 5. Insertar registros de prueba para reportes (Atrasos y Salidas Anticipadas)
-- Registro normal (Sin atraso ni salida anticipada)
INSERT INTO asistencia (usuario_id, fecha, hora_entrada, hora_salida)
VALUES (2, CURRENT_DATE(), '08:55:00', '18:00:00');

-- Registro con Atraso (Entrada despues de las 09:30 AM)
INSERT INTO asistencia (usuario_id, fecha, hora_entrada, hora_salida)
VALUES (2, DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY), '09:45:00', '18:00:00');

-- Registro con Salida Anticipada (Salida antes de las 17:30 PM)
INSERT INTO asistencia (usuario_id, fecha, hora_entrada, hora_salida)
VALUES (3, DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY), '09:00:00', '16:15:00');

