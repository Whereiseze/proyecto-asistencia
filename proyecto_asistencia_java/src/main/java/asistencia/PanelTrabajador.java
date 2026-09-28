package asistencia;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class PanelTrabajador extends JFrame {

    private final Usuario usuario;

    public PanelTrabajador(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Sistema de Asistencia - Trabajador");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
    }

    private void crearInterfaz() {
        JPanel principal = new JPanel(new BorderLayout(15, 15));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        // TÍTULO
        JLabel titulo = new JLabel(
                "PANEL DEL TRABAJADOR",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        principal.add(
                titulo,
                BorderLayout.NORTH
        );

        // INFORMACIÓN DEL USUARIO
        JPanel informacion = new JPanel(
                new GridLayout(2, 1)
        );

        JLabel nombre = new JLabel(
                "Bienvenido: " + usuario.getNombre(),
                SwingConstants.CENTER
        );

        JLabel correo = new JLabel(
                "Correo: " + usuario.getCorreo(),
                SwingConstants.CENTER
        );

        nombre.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        correo.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        informacion.add(nombre);
        informacion.add(correo);

        // BOTONES
        JPanel botones = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        JButton btnEntrada = new JButton("REGISTRAR ENTRADA");
        JButton btnSalida = new JButton("REGISTRAR SALIDA");
        JButton btnAsistencias = new JButton("MIS ASISTENCIAS");
        JButton btnCerrar = new JButton("CERRAR SESIÓN");

        botones.add(btnEntrada);
        botones.add(btnSalida);
        botones.add(btnAsistencias);
        botones.add(btnCerrar);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(informacion, BorderLayout.NORTH);
        centro.add(botones, BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);

        // REGISTRAR ENTRADA
        btnEntrada.addActionListener(e -> registrarEntrada());

        // REGISTRAR SALIDA
        btnSalida.addActionListener(e -> registrarSalida());

        // VER ASISTENCIAS
        btnAsistencias.addActionListener(e -> mostrarMisAsistencias());

        // CERRAR SESIÓN
        btnCerrar.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea cerrar sesión?",
                    "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
            }
        });

        add(principal);
    }

    private void registrarEntrada() {
        String sql = "INSERT INTO asistencia (usuario_id, fecha, hora_entrada) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setTime(3, Time.valueOf(LocalTime.now()));

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Entrada registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar entrada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarSalida() {
        String sql = "UPDATE asistencia SET hora_salida = ? WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(LocalTime.now()));
            stmt.setInt(2, usuario.getId());
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Salida registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró registro de entrada para el día de hoy.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar salida: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMisAsistencias() {
        String sql = "SELECT fecha, hora_entrada, hora_salida FROM asistencia WHERE usuario_id = ? ORDER BY fecha DESC";
        StringBuilder sb = new StringBuilder();
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sb.append("Fecha: ").append(rs.getDate("fecha"))
                            .append(" | Entrada: ").append(rs.getTime("hora_entrada"))
                            .append(" | Salida: ").append(rs.getTime("hora_salida") != null ? rs.getTime("hora_salida") : "Pendiente")
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener asistencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sb.length() == 0) {
            sb.append("No registra asistencias hasta la fecha.");
        }

        JTextArea areaTexto = new JTextArea(sb.toString());
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(450, 250));
        JOptionPane.showMessageDialog(this, scroll, "Mis Asistencias", JOptionPane.INFORMATION_MESSAGE);
    }
}