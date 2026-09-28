package asistencia;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelAdministrador extends JFrame {

    private final Usuario usuario;

    public PanelAdministrador(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Sistema de Asistencia - Administrador");
        setSize(650, 650);
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

        // TITULO
        JLabel titulo = new JLabel(
                "PANEL DEL ADMINISTRADOR",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        principal.add(titulo, BorderLayout.NORTH);

        // INFORMACION DEL USUARIO
        JPanel informacion = new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel nombre = new JLabel(
                "Usuario: " + usuario.getNombre(),
                SwingConstants.CENTER
        );

        JLabel cargo = new JLabel(
                "Rol: " + usuario.getRol(),
                SwingConstants.CENTER
        );

        nombre.setFont(new Font("Arial", Font.PLAIN, 16));
        cargo.setFont(new Font("Arial", Font.PLAIN, 16));

        informacion.add(nombre);
        informacion.add(cargo);

        // PANEL DE BOTONES
        JPanel botones = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnReporteAtrasos = new JButton("REPORTE DE ATRASOS (RE-01)");
        JButton btnReporteSalidas = new JButton("REPORTE DE SALIDAS ANTICIPADAS (RE-02)");
        JButton btnReporteInasistencias = new JButton("REPORTE DE INASISTENCIAS (RE-03)");
        JButton btnGestionUsuarios = new JButton("GESTIONAR USUARIOS (GU-01/02/03)");
        JButton btnCerrarSesion = new JButton("CERRAR SESION");

        botones.add(btnReporteAtrasos);
        botones.add(btnReporteSalidas);
        botones.add(btnReporteInasistencias);
        botones.add(btnGestionUsuarios);
        botones.add(btnCerrarSesion);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(informacion, BorderLayout.NORTH);
        centro.add(botones, BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);

        add(principal);

        // EVENTOS DE LOS BOTONES
        btnReporteAtrasos.addActionListener(e -> {
            Reporte rep = new Reporte();
            List<String> atrasos = rep.obtenerReporteAtrasos();
            mostrarResultadoReporte("Reporte de Atrasos (Post 9:30 AM)", atrasos);
        });

        btnReporteSalidas.addActionListener(e -> {
            Reporte rep = new Reporte();
            List<String> salidas = rep.obtenerReporteSalidasAnticipadas();
            mostrarResultadoReporte("Reporte de Salidas Anticipadas (Antes 17:30 PM)", salidas);
        });

        btnReporteInasistencias.addActionListener(e -> {
            String fecha = JOptionPane.showInputDialog(this, "Ingrese la fecha a consultar (YYYY-MM-DD):", "Consulta de Inasistencias", JOptionPane.QUESTION_MESSAGE);
            if (fecha != null && !fecha.trim().isEmpty()) {
                Reporte rep = new Reporte();
                List<String> inasistencias = rep.obtenerReporteInasistencias(fecha.trim());
                mostrarResultadoReporte("Reporte de Inasistencias - Fecha: " + fecha, inasistencias);
            }
        });

        btnGestionUsuarios.addActionListener(e -> {
            FormularioCrearUsuario form = new FormularioCrearUsuario();
            form.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
        });
    }

    private void mostrarResultadoReporte(String titulo, List<String> datos) {
        StringBuilder sb = new StringBuilder();
        if (datos.isEmpty()) {
            sb.append("No se encontraron registros para este reporte.");
        } else {
            for (String linea : datos) {
                sb.append(linea).append("\n");
            }
        }
        JTextArea areaTexto = new JTextArea(sb.toString());
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}