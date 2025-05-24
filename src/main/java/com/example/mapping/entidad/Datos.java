package com.example.mapping.entidad;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Datos {

    @Id
    private String id;
    private long numIdentificacion;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private String rol;

    public Datos() {}

    public Datos(long numIdentificacion, String nombre, String apellido, String email, String contraseña, String rol) {
        this.numIdentificacion = numIdentificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public long getNumIdentificacion() {
        return numIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "numIdentificacion=" + numIdentificacion +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
