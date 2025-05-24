package com.example.mapping.entidad;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "rutas")
public class Rutas {

    @Id
    private String id;
    private ObjectId lugarId;
    private String origen;
    private String destino;
    private String ruta;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectId getLugarId() {
        return lugarId;
    }

    public void setLugarId(ObjectId lugarId) {
        this.lugarId = lugarId;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }



}
