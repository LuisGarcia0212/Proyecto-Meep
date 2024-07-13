package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Chat implements Serializable {
    private int idMensajeria;
    private int idUsuario1;
    private String userEmisor;
    private String fotoEmisor;
    private int idUsuario2;
    private String userReceptor;
    private String foto_receptor;
    private String mensaje;
    private String Fecha;
    private String hora;

    public Chat() {
    }

    public Chat(int idMensajeria, int idUsuario1, String userEmisor, String fotoEmisor, int idUsuario2, String userReceptor, String foto_receptor, String mensaje, String fecha, String hora) {
        this.idMensajeria = idMensajeria;
        this.idUsuario1 = idUsuario1;
        this.userEmisor = userEmisor;
        this.fotoEmisor = fotoEmisor;
        this.idUsuario2 = idUsuario2;
        this.userReceptor = userReceptor;
        this.foto_receptor = foto_receptor;
        this.mensaje = mensaje;
        Fecha = fecha;
        this.hora = hora;
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

    public String getUserEmisor() {
        return userEmisor;
    }

    public void setUserEmisor(String userEmisor) {
        this.userEmisor = userEmisor;
    }

    public String getFotoEmisor() {
        return fotoEmisor;
    }

    public void setFotoEmisor(String fotoEmisor) {
        this.fotoEmisor = fotoEmisor;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    public String getUserReceptor() {
        return userReceptor;
    }

    public void setUserReceptor(String userReceptor) {
        this.userReceptor = userReceptor;
    }

    public String getFoto_receptor() {
        return foto_receptor;
    }

    public void setFoto_receptor(String foto_receptor) {
        this.foto_receptor = foto_receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
