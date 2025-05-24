package com.example.mapping.repositorio;
import com.example.mapping.entidad.Rutas;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RutaRepository extends MongoRepository<Rutas, String> { ;
    List<Rutas> findByLugarId(ObjectId lugarId);
    void deleteByOrigenOrDestino(String origen, String destino);
    List<Rutas> findByOrigenIgnoreCaseAndDestinoIgnoreCase(String origen, String destino);


}