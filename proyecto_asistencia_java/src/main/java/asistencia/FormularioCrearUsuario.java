package asistencia;

import javax.swing.*;
import java.awt.*;

public class FormularioCrearUsuario extends JFrame {

    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JButton btnGuardar;

    public FormularioCrearUsuario() {
        setTitle("GU-01: Crear Nuevo Usuario");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel(" RUT:"));
        txtRut = new JTextField();
        add(txtRut);

        add(new JLabel(" Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel(" Correo:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel(" Contraseña:"));
        txtContrasena = new JPasswordField();
        add(txtContrasena);

        add(new JLabel(" Rol:"));
        cbRol = new JComboBox<>(new String[]{"TRABAJADOR", "ADMIN"});
        add(cbRol);

        btnGuardar = new JButton("Guardar Usuario");
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarUsuario());
    }

    private void guardarUsuario() {
        String rut = txtRut.getText().trim();
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        String rol = (String) cbRol.getSelectedItem();

        if (rut.isEmpty() || nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(rut, nombre, correo, contrasena, rol);
        GestionUsuarios gestion = new GestionUsuarios();

        if (gestion.crearUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el usuario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}