package com.example.mapping.servicio;

import com.example.mapping.entidad.Datos;
import com.example.mapping.repositorio.DatosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.example.mapping.repositorio.LugarRepositorio;
import java.util.List;
import java.util.Optional;

@Service
public class DatosServicio {

    @Autowired
    private DatosRepositorio datosRepositorio;


    @Autowired
    private MongoTemplate mongoTemplate;

    public Datos guardarDatos(Datos datos) {
        return datosRepositorio.save(datos);
    }

    public boolean verificarDatos(String email, String contraseña) {
        Datos usuario = datosRepositorio.findByEmailAndContraseña(email, contraseña);
        return usuario != null;
    }

    public boolean existePorId(long num_identificacion) {
        Query query = new Query(Criteria.where("num_identificacion").is(num_identificacion));
        return mongoTemplate.exists(query, "usuarios");
    }

    public boolean existePorEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.exists(query, "usuarios");
    }

    public Datos obtenerPorEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, Datos.class, "usuarios");
    }

    public List<Datos> obtenerTodos() {
        return datosRepositorio.findAll();
    }

    public void cambiarRol(String id) {
        Optional<Datos> optional = datosRepositorio.findById(id);
        if (optional.isPresent()) {
            Datos usuario = optional.get();
            String nuevoRol = usuario.getRol().equals("Admin") ? "Usuario" : "Admin";
            usuario.setRol(nuevoRol);
            datosRepositorio.save(usuario);
        }
    }

    public void eliminarUsuario(String id) {
        datosRepositorio.deleteById(id);
    }



}
