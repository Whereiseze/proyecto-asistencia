package asistencia;

import javax.swing.SwingUtilities;

public class Main {
 public static void main(String[] args) {
  System.out.println("Sistema de Asistencia iniciado correctamente.");

  SwingUtilities.invokeLater(() -> {
   Login ventana = new Login();
   ventana.setVisible(true);
  });
 }
}