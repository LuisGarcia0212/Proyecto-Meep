package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Proyectos implements Serializable {
    private int idProyecto;
    private String estado;
    private String nombreProyecto;
    private String descripcionProyecto;
    private int id_rol;

    public Proyectos(String nombreProyec, String foto, String nombresCompletos, int idRol) {
    }

    public Proyectos(int idProyecto, String estado, String nombreProyecto, String descripcionProyecto, int id_rol) {
        this.idProyecto = idProyecto;
        this.estado = estado;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.id_rol = id_rol;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}
