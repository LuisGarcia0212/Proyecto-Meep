package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Miembro implements Serializable {

    private String nombreProyecto;
    private String fotoParticipante;
    private String nombreParticipante;
    private int Rol;

    public Miembro() {
    }

    public Miembro(String nombreProyecto, String fotoParticipante, String nombreParticipante, int rol) {
        this.nombreProyecto = nombreProyecto;
        this.fotoParticipante = fotoParticipante;
        this.nombreParticipante = nombreParticipante;
        Rol = rol;
    }

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
