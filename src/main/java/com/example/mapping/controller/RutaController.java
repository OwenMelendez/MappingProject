package com.example.mapping.controller;

import com.example.mapping.entidad.Rutas;
import com.example.mapping.servicio.LugarServicios;
import com.example.mapping.servicio.RutaService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RutaController {

    @Autowired
    private LugarServicios lugarServicios;

    @Autowired
    private RutaService rutaService;

    @GetMapping("/irRuta")
    public String procesarRuta(HttpSession session, Model model) {
        String seleccion = (String) session.getAttribute("seleccion");
        if (seleccion == null || seleccion.isBlank()) {
            model.addAttribute("mensaje", "No se ha recibido la selección.");
            return "lugar_de_encuentro";
        }

        ObjectId lugarObjectId = lugarServicios.obtenerObjectIdPorNombre(seleccion);
        if (lugarObjectId == null) {
            model.addAttribute("mensaje", "Lugar no encontrado");
            return "buscar_ruta";
        }

        List<String> rutas = rutaService.obtenerRutasPorLugar(lugarObjectId.toString())
                .stream().map(Rutas::getDestino).collect(Collectors.toList());

        model.addAttribute("nombre", seleccion);
        model.addAttribute("piso", lugarServicios.obtenerPiso(seleccion));
        model.addAttribute("bloque", lugarServicios.obtenerBloque(seleccion));
        model.addAttribute("categoria", lugarServicios.obtenerCategoria(seleccion));
        model.addAttribute("rutas", rutas);
        model.addAttribute("oid", lugarObjectId);

        return "buscar_ruta";
    }


    @PostMapping("/irRuta")
    public String verificarRuta(@RequestParam String seleccion, HttpSession session, Model model) {

        String nombre = (String) session.getAttribute("seleccion");

        if (nombre == null) {

            return "redirect:/BusquedaSalones";
        }

        ObjectId oid = lugarServicios.obtenerObjectIdPorNombre(nombre);
        System.out.println("id: " + oid + " nombre: " + nombre);

        List<String> rutas = rutaService.obtenerRutasPorLugar(oid.toString())
                .stream().map(r -> r.getDestino()).toList();

        boolean rutaExiste = rutas.stream().anyMatch(r -> r.equalsIgnoreCase(seleccion));

        if (rutaExiste) {
            session.setAttribute("rutas", rutas);
            session.setAttribute("rutaSeleccionada", seleccion);
            return "redirect:/rutas";
        } else {
            model.addAttribute("mensaje", "La ruta seleccionada no existe. Por favor, elige otra.");
            model.addAttribute("nombre", nombre);
            model.addAttribute("piso", lugarServicios.obtenerPiso(nombre));
            model.addAttribute("bloque", lugarServicios.obtenerBloque(nombre));
            model.addAttribute("categoria", lugarServicios.obtenerCategoria(nombre));
            model.addAttribute("rutas", rutas);
            model.addAttribute("oid", oid);
            return "buscar_ruta";
        }
    }


    @GetMapping("/rutas")
    public String mostrarVideo(HttpSession session, Model model) {
        String rutaSeleccionada = (String) session.getAttribute("rutaSeleccionada");
        String nombreLugar = (String) session.getAttribute("seleccion");

        System.out.println("ORIGEN desde sesión: " + nombreLugar);
        System.out.println("DESTINO desde sesión: " + rutaSeleccionada);


        if (rutaSeleccionada == null || nombreLugar == null) {
            model.addAttribute("mensaje", "No se ha seleccionado una ruta válida.");
            return "lugar_de_encuentro";
        }

        Rutas ruta = rutaService.buscarUnaPorOrigenYDestino(nombreLugar, rutaSeleccionada);
        System.out.println("url rutas" + ruta.getRuta());

        if (ruta != null && ruta.getRuta() != null) {
            model.addAttribute("videoUrl", "/" + ruta.getRuta());
        }else {
            model.addAttribute("mensaje", "Esta ruta aún no tiene video disponible.");
        }

        return "rutas";
    }







}
