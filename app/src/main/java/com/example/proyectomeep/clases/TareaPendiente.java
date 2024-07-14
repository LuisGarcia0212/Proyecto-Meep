package com.example.proyectomeep.clases;

import java.io.Serializable;

public class TareaPendiente implements Serializable {
    private int idTarea;
    private String nombreTarea;
    private String descripcionTarea;
    private int idUsTarea;
    private int idProyectoTarea;
    private String estado;
    private String nombreProyecto;

    public TareaPendiente() {
    }

    public TareaPendiente(int idTarea, String nombreTarea, String descripcionTarea, int idUsTarea, int idProyectoTarea, String estado, String nombreProyecto) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.idUsTarea = idUsTarea;
        this.idProyectoTarea = idProyectoTarea;
        this.estado = estado;
        this.nombreProyecto = nombreProyecto;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public int getIdUsTarea() {
        return idUsTarea;
    }

    public void setIdUsTarea(int idUsTarea) {
        this.idUsTarea = idUsTarea;
    }

    public int getIdProyectoTarea() {
        return idProyectoTarea;
    }

    public void setIdProyectoTarea(int idProyectoTarea) {
        this.idProyectoTarea = idProyectoTarea;
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
}
