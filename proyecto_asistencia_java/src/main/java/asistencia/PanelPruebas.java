package asistencia;

import javax.swing.*;
import java.awt.*;

public class PanelPruebas extends JFrame {

    private Usuario usuario;

    public PanelPruebas() {

        setTitle("Sistema de Asistencia - Panel de Pruebas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
        cargarUsuarioPrueba();
    }

    private void cargarUsuarioPrueba() {

        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try (
                var conexion = Conexion.obtenerConexion();
                var sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, "juan@empresa.cl");

            try (var resultado = sentencia.executeQuery()) {

                if (resultado.next()) {

                    usuario = new Usuario(
                            resultado.getInt("id"),
                            resultado.getString("rut"),
                            resultado.getString("nombre"),
                            resultado.getString("correo"),
                            resultado.getString("contrasena"),
                            resultado.getString("rol")
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró el usuario de prueba."
                    );
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar usuario:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void crearInterfaz() {

        JPanel principal = new JPanel(new BorderLayout(15, 15));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // TITULO

        JLabel titulo = new JLabel(
                "PANEL DE PRUEBAS",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        principal.add(titulo, BorderLayout.NORTH);

        // BOTONES

        JPanel botones = new JPanel(
                new GridLayout(6, 1, 10, 10)
        );

        JButton btnEntrada =
                new JButton("REGISTRAR ENTRADA");

        JButton btnSalida =
                new JButton("REGISTRAR SALIDA");

        JButton btnAsistencias =
                new JButton("MIS ASISTENCIAS");

        JButton btnAtrasos =
                new JButton("REPORTE DE ATRASOS");

        JButton btnSalidas =
                new JButton("SALIDAS ANTICIPADAS");

        JButton btnLogin =
                new JButton("PROBAR LOGIN");

        botones.add(btnEntrada);
        botones.add(btnSalida);
        botones.add(btnAsistencias);
        botones.add(btnAtrasos);
        botones.add(btnSalidas);
        botones.add(btnLogin);

        principal.add(botones, BorderLayout.CENTER);

        // INFORMACION

        JLabel usuarioLabel = new JLabel(
                "Usuario de prueba: Juan Perez",
                SwingConstants.CENTER
        );

        usuarioLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        principal.add(
                usuarioLabel,
                BorderLayout.SOUTH
        );

        add(principal);

        // EVENTOS

        btnEntrada.addActionListener(e -> {
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Prueba: Registro de entrada para " + usuario.getNombre());
            }
        });

        btnSalida.addActionListener(e -> {
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Prueba: Registro de salida para " + usuario.getNombre());
            }
        });

        btnAsistencias.addActionListener(e -> {
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Prueba: Mostrando asistencias de " + usuario.getNombre());
            }
        });

        btnAtrasos.addActionListener(e -> {
            Reporte rep = new Reporte();
            java.util.List<String> atrasos = rep.obtenerReporteAtrasos();
            JOptionPane.showMessageDialog(this, "Atrasos encontrados: " + atrasos.size());
        });

        btnSalidas.addActionListener(e -> {
            Reporte rep = new Reporte();
            java.util.List<String> salidas = rep.obtenerReporteSalidasAnticipadas();
            JOptionPane.showMessageDialog(this, "Salidas anticipadas encontradas: " + salidas.size());
        });

        btnLogin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo modulo de Login...");
        });
    }
}