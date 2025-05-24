package com.example.mapping.servicio;

import com.example.mapping.entidad.Lugar;
import com.example.mapping.repositorio.LugarRepositorio;
import com.example.mapping.repositorio.RutaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LugarServicios {

    @Autowired
    private LugarRepositorio lugarRepositorio;

    @Autowired
    private RutaRepository rutaRepository;

    public List<String> obtenerNombre() {
        return lugarRepositorio.findAll()
                .stream()
                .map(Lugar::getNombre)
                .collect(Collectors.toList());
    }

    public String obtenerPiso(String nombre) {
        Lugar lugar = lugarRepositorio.findByNombre(nombre);
        return (lugar != null) ? lugar.getPiso() : null;
    }

    public String obtenerBloque(String nombre) {
        Lugar lugar = lugarRepositorio.findByNombre(nombre);
        return (lugar != null) ? lugar.getBloque() : null;
    }

    public String obtenerCategoria(String nombre) {
        Lugar lugar = lugarRepositorio.findByNombre(nombre);
        return (lugar != null) ? lugar.getCategoria() : null;
    }
    public ObjectId obtenerObjectIdPorNombre(String nombre) {
        Lugar lugar = lugarRepositorio.findByNombre(nombre);
        if (lugar != null) {
            return new ObjectId(lugar.getId());
        }
        return null;
    }

    public Lugar agregarLugar(Lugar lugar) {
        return lugarRepositorio.save(lugar);
    }


    public void eliminarLugarYSusRutas(String lugarId) {
        Optional<Lugar> lugarOptional = lugarRepositorio.findById(lugarId);

        if (lugarOptional.isPresent()) {
            Lugar lugar = lugarOptional.get();
            String nombreLugar = lugar.getNombre();

            rutaRepository.deleteByOrigenOrDestino(nombreLugar, nombreLugar);

            lugarRepositorio.deleteById(lugarId);
        }
    }



}
