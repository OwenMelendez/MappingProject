package com.example.mapping.controller;

import com.example.mapping.entidad.Datos;
import com.example.mapping.entidad.Lugar;
import com.example.mapping.entidad.Rutas;
import com.example.mapping.repositorio.LugarRepositorio;
import com.example.mapping.servicio.DatosServicio;
import com.example.mapping.servicio.LugarServicios;
import com.example.mapping.servicio.RutaService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private LugarServicios lugarServicios;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private LugarRepositorio lugarRepositorio;

    @Autowired
    private DatosServicio datos;


    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return rol != null && rol.equals("Admin");
    }

    @GetMapping("/admin")
    public String panelAdmin(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        List<Lugar> lugares = lugarRepositorio.findAll();
        model.addAttribute("lugares", lugares);
        return "admin";
    }

    @PostMapping("/agregar")
    public String agregarLugar(@RequestParam String nombre,
                               @RequestParam String piso,
                               @RequestParam String bloque,
                               @RequestParam String categoria,
                               HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        Lugar lugar = new Lugar(nombre, piso, bloque, categoria);
        lugarServicios.agregarLugar(lugar);
        return "redirect:/admin";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @PostMapping("/rutas")
    public String agregarRuta(@RequestParam String lugarId,
                              @RequestParam String origen,
                              @RequestParam String destino,
                              HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        Rutas ruta = new Rutas();
        ruta.setLugarId(new ObjectId(lugarId));
        ruta.setOrigen(origen);
        ruta.setDestino(destino);
        rutaService.agregarRuta(ruta);
        return "redirect:/admin";
    }

    @GetMapping("/agregarRutas")
    public String mostrarFormularioRuta(Model model, HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        model.addAttribute("nombres", lugarServicios.obtenerNombre());
        return "agregar_ruta";
    }

    @PostMapping("/agregarRutas")
    public String procesarFormularioRuta(@RequestParam String nombre,
                                         @RequestParam String origen,
                                         @RequestParam String destino,
                                         HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/inicio";
        }

        ObjectId lugarId = lugarServicios.obtenerObjectIdPorNombre(nombre);
        if (lugarId != null) {
            Rutas ruta = new Rutas();
            ruta.setLugarId(lugarId);
            ruta.setOrigen(origen);
            ruta.setDestino(destino);
            rutaService.agregarRuta(ruta);
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/eliminar/{id}")
    public String eliminarLugar(@PathVariable("id") String id, HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        lugarServicios.eliminarLugarYSusRutas(id);
        return "redirect:/admin";
    }

    @GetMapping("/gestionUsuarios")
    public String usuarios(Model model, HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        List<Datos> listaUsuarios = datos.obtenerTodos();
        model.addAttribute("usuarios", listaUsuarios);
        return "gestion_usuarios";
    }

    @PostMapping("/cambiarRol")
    public String cambiarRol(@RequestParam String id, HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        datos.cambiarRol(id);
        return "redirect:/gestionUsuarios";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id, HttpSession session) {
        if (!esAdmin(session)) {
            return "acceso_denegado";
        }

        datos.eliminarUsuario(id);
        return "redirect:/gestionUsuarios";
    }
}
