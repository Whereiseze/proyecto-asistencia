package asistencia;

import java.sql.Date;
import java.sql.Time;

public class Asistencia {
 private int id;
 private int usuarioId;
 private Date fecha;
 private Time horaEntrada;
 private Time horaSalida;

 public Asistencia() {}

 public Asistencia(int id, int usuarioId, Date fecha, Time horaEntrada, Time horaSalida) {
  this.id = id;
  this.usuarioId = usuarioId;
  this.fecha = fecha;
  this.horaEntrada = horaEntrada;
  this.horaSalida = horaSalida;
 }

 public int getId() { return id; }
 public void setId(int id) { this.id = id; }

 public int getUsuarioId() { return usuarioId; }
 public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

 public Date getFecha() { return fecha; }
 public void setFecha(Date fecha) { this.fecha = fecha; }

 public Time getHoraEntrada() { return horaEntrada; }
 public void setHoraEntrada(Time horaEntrada) { this.horaEntrada = horaEntrada; }

 public Time getHoraSalida() { return horaSalida; }
 public void setHoraSalida(Time horaSalida) { this.horaSalida = horaSalida; }
}