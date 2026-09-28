package asistencia;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public Login() {
        setTitle("Sistema de Asistencia - Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
    }

    private void crearInterfaz() {
        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(2, 2, 10, 10));

        formulario.add(new JLabel("Correo electrónico:"));
        txtCorreo = new JTextField();
        formulario.add(txtCorreo);

        formulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        formulario.add(txtContrasena);

        principal.add(formulario, BorderLayout.CENTER);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        principal.add(btnIngresar, BorderLayout.SOUTH);

        add(principal);

        btnIngresar.addActionListener(e -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese correo y contraseña.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = autenticarEnBaseDeDatos(correo, contrasena);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre(), "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

            if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                new PanelAdministrador(usuario).setVisible(true);
            } else {
                new PanelTrabajador(usuario).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario no registrado.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Usuario autenticarEnBaseDeDatos(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (var conn = Conexion.obtenerConexion();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("rut"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contrasena"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con MySQL en XAMPP: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}