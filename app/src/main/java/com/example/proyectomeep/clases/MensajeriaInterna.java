package com.example.proyectomeep.clases;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class MensajeriaInterna implements Serializable {

    private int idMensajeria;
    private int idUsuario1;
    private int idUsuario2;
    private String Username2;
    private String mensaje;
    private String hora;
    private String fecha;
    private String foto;

    public MensajeriaInterna() {
    }

    public MensajeriaInterna(int idMensajeria, int idUsuario1, int idUsuario2, String username2, String mensaje, String hora, String fecha, String foto) {
        this.idMensajeria = idMensajeria;
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
        Username2 = username2;
        this.mensaje = mensaje;
        this.hora = hora;
        this.fecha = fecha;
        this.foto = foto;
    }

    public int getIdMensajeria() {
        return idMensajeria;
    }

    public void setIdMensajeria(int idMensajeria) {
        this.idMensajeria = idMensajeria;
    }

    public int getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(int idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    public String getUsername2() {
        return Username2;
    }

    public void setUsername2(String username2) {
        Username2 = username2;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
