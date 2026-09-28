package asistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
 private static final String URL = "jdbc:mysql://localhost:3306/asistencia_db";
 private static final String USER = "root";
 private static final String PASSWORD = "";

 public static Connection obtenerConexion() throws SQLException {
  try {
   Class.forName("com.mysql.cj.jdbc.Driver");
   return DriverManager.getConnection(URL, USER, PASSWORD);
  } catch (ClassNotFoundException e) {
   throw new SQLException("Driver JDBC de MySQL no encontrado.", e);
  }
 }
}