package com.example.interfaznegocio.models;

public class Cliente {
    private Long id;
    private String codigo;
    private String nombre;
    private String email;
    private String direccion;

    public Cliente() {
    }

    public Cliente(Long id, String codigo, String nombre, String email, String direccion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
