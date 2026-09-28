package asistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reporte {

 // RE-01: Reporte de atrasos (Entrada despues de las 09:30:00)
 public List<String> obtenerReporteAtrasos() {
  List<String> resultados = new ArrayList<>();
  String sql = "SELECT u.rut, u.nombre, a.fecha, a.hora_entrada " +
          "FROM asistencia a " +
          "JOIN usuarios u ON a.usuario_id = u.id " +
          "WHERE a.hora_entrada > '09:30:00'";
  try (Connection conn = Conexion.obtenerConexion();
       PreparedStatement stmt = conn.prepareStatement(sql);
       ResultSet rs = stmt.executeQuery()) {
   while (rs.next()) {
    resultados.add("ID/RUT: " + rs.getString("rut") +
            " | Nombre: " + rs.getString("nombre") +
            " | Fecha: " + rs.getDate("fecha") +
            " | Hora Entrada: " + rs.getTime("hora_entrada"));
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return resultados;
 }

 // RE-02: Reporte de salidas anticipadas (Salida antes de las 17:30:00)
 public List<String> obtenerReporteSalidasAnticipadas() {
  List<String> resultados = new ArrayList<>();
  String sql = "SELECT u.rut, u.nombre, a.fecha, a.hora_salida " +
          "FROM asistencia a " +
          "JOIN usuarios u ON a.usuario_id = u.id " +
          "WHERE a.hora_salida IS NOT NULL AND a.hora_salida < '17:30:00'";
  try (Connection conn = Conexion.obtenerConexion();
       PreparedStatement stmt = conn.prepareStatement(sql);
       ResultSet rs = stmt.executeQuery()) {
   while (rs.next()) {
    resultados.add("ID/RUT: " + rs.getString("rut") +
            " | Nombre: " + rs.getString("nombre") +
            " | Fecha: " + rs.getDate("fecha") +
            " | Hora Salida: " + rs.getTime("hora_salida"));
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return resultados;
 }

 // RE-03: Reporte de inasistencias (Trabajadores sin entrada ni salida en una fecha dada)
 public List<String> obtenerReporteInasistencias(String fecha) {
  List<String> resultados = new ArrayList<>();
  String sql = "SELECT u.rut, u.nombre " +
          "FROM usuarios u " +
          "WHERE u.rol = 'TRABAJADOR' AND u.id NOT IN (" +
          "  SELECT usuario_id FROM asistencia WHERE fecha = ?" +
          ")";
  try (Connection conn = Conexion.obtenerConexion();
       PreparedStatement stmt = conn.prepareStatement(sql)) {
   stmt.setString(1, fecha);
   try (ResultSet rs = stmt.executeQuery()) {
    while (rs.next()) {
     resultados.add("ID/RUT: " + rs.getString("rut") +
             " | Nombre: " + rs.getString("nombre") +
             " | Estado: Inasistencia el " + fecha);
    }
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return resultados;
 }
}