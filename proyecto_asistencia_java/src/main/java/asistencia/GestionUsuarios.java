package asistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarios {

    // GU-01: Crear usuarios
    public boolean crearUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (rut, nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getRut());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getCorreo());
            stmt.setString(4, u.getContrasena());
            stmt.setString(5, u.getRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GU-02: Modificar usuarios
    public boolean modificarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET rut = ?, nombre = ?, correo = ?, contrasena = ?, rol = ? WHERE id = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getRut());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getCorreo());
            stmt.setString(4, u.getContrasena());
            stmt.setString(5, u.getRol());
            stmt.setInt(6, u.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GU-03: Eliminar usuarios
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener lista completa de usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("rut"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}