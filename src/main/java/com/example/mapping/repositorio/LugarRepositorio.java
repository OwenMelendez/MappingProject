package com.example.mapping.repositorio;


import com.example.mapping.entidad.Lugar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LugarRepositorio extends MongoRepository<Lugar, String> {


    Lugar findByNombre(String nombre);
}

