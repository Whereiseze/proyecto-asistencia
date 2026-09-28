package asistencia;

public class Administrador {

 private final Usuario usuario;

 public Administrador(Usuario usuario) {
  this.usuario = usuario;
 }

 public void mostrarMenu() {
  System.out.println("\n============================================");
  System.out.println("       MENU DE ASISTENCIA");
  System.out.println("============================================");
  System.out.println("Usuario: " + usuario.getNombre());
  System.out.println("Cargo: " + usuario.getRol());
  System.out.println("--------------------------------------------");
  System.out.println("1. Registrar entrada");
  System.out.println("2. Registrar salida");
  System.out.println("3. Reporte de atrasos");
  System.out.println("4. Reporte de salidas anticipadas");
  System.out.println("5. Reporte de inasistencias");
  System.out.println("6. Gestionar usuarios");
  System.out.println("7. Cerrar sesion");
  System.out.println("============================================");
 }
}