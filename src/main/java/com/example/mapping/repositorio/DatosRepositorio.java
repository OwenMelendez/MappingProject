package com.example.mapping.repositorio;

import com.example.mapping.entidad.Datos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatosRepositorio extends MongoRepository<Datos, String> {
    Datos findByEmailAndContraseña(String email, String contraseña);
    boolean existsByEmail(String email);
}