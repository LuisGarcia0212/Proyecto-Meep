package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Proyectos implements Serializable {
    private int idProyecto;
    private String estado;
    private String nombreProyecto;
    private String descripcionProyecto;
    private boolean pinned;
    private boolean favorite;
    private int id_rol;

    public Proyectos(int idProyecto, String estado, String nombreProyecto, String descripcionProyecto, boolean pinned, boolean favorite, int id_rol) {
        this.idProyecto = idProyecto;
        this.estado = estado;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.pinned = pinned;
        this.favorite = favorite;
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

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}