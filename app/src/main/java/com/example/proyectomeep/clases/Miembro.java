package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Miembro implements Serializable {
    private int idParticipante;
    private String nombreProyecto;
    private String fotoParticipante;
    private String nombreParticipante;
    private int Rol;

    public Miembro() {
    }

    public Miembro(int idParticipante, String nombreProyecto, String fotoParticipante, String nombreParticipante, int rol) {
        this.idParticipante = idParticipante;
        this.nombreProyecto = nombreProyecto;
        this.fotoParticipante = fotoParticipante;
        this.nombreParticipante = nombreParticipante;
        Rol = rol;
    }
    public int getIdParticipante() { return idParticipante ;}

    public void setIdParticipante(int idParticipante) { this.idParticipante = idParticipante; }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getFotoParticipante() {
        return fotoParticipante;
    }

    public void setFotoParticipante(String fotoParticipante) {
        this.fotoParticipante = fotoParticipante;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }

    public int getRol() {
        return Rol;
    }

    public void setRol(int rol) {
        Rol = rol;
    }
}
