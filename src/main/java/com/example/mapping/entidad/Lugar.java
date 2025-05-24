package com.example.mapping.entidad;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lugares")
public class Lugar {

    @Id
    private String id;
    private String nombre;
    private String piso;
    private String bloque;
    private String categoria;


    public Lugar() {}


    public Lugar(String nombre, String piso, String bloque, String categoria) {
        this.nombre = nombre;
        this.piso = piso;
        this.bloque = bloque;
        this.categoria = categoria;
    }


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPiso() {
        return piso;
    }

    public String getBloque() {
        return bloque;
    }

    public String getCategoria() {
        return categoria;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
