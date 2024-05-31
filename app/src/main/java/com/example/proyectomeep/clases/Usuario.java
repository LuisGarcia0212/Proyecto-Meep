package com.example.proyectomeep.clases;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int idUsuario;
    private String nombresC;
    private String usuario;
    private String clave;
    private String email;
    private String telefono;
    private String direccion;
    private String descripcion;
    private String Foto;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombresC, String usuario, String clave, String email, String telefono, String direccion, String descripcion, String foto) {
        this.idUsuario = idUsuario;
        this.nombresC = nombresC;
        this.usuario = usuario;
        this.clave = clave;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.descripcion = descripcion;
        Foto = foto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombresC() {
        return nombresC;
    }

    public void setNombresC(String nombresC) {
        this.nombresC = nombresC;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
