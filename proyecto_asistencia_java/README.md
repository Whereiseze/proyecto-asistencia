# Proyecto Asistencia - Java
Conversión a Java 17 + MySQL.

Requisitos: JDK 17+, Maven y MySQL.
1. Ejecuta `base_datos.sql`.
2. Si corresponde, cambia usuario/contraseña en `Conexion.java`.
3. Ejecuta `mvn clean compile` y luego `mvn exec:java`.

Usuarios de prueba: admin@empresa.cl / 1234 y juan@empresa.cl / 1234.

También se corrigieron los errores del Python original: `conectar_bd()` inexistente en reportes y las opciones 3/4/5 desplazadas en `main.py`.
