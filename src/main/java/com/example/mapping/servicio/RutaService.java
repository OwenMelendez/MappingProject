package com.example.mapping.servicio;

import com.example.mapping.entidad.Rutas;
import com.example.mapping.repositorio.RutaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.Collections;
import java.util.List;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;



    public List<Rutas> obtenerRutasPorLugar(String lugarId) {
        if (lugarId == null || lugarId.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            ObjectId objectId = new ObjectId(lugarId);
            return rutaRepository.findByLugarId(objectId);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    public Rutas agregarRuta(Rutas ruta) {
        return rutaRepository.save(ruta);
    }

    public Rutas buscarUnaPorOrigenYDestino(String origen, String destino) {
        List<Rutas> rutas = rutaRepository.findByOrigenIgnoreCaseAndDestinoIgnoreCase(origen, destino);
        return rutas.isEmpty() ? null : rutas.get(0);
    }






}
