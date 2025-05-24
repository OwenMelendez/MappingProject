package com.example.mapping.controller;

import com.example.mapping.servicio.LugarServicios;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LugarController {

    @Autowired
    private LugarServicios lugarServicios;

    @GetMapping("/BusquedaSalones")
    public String abrirBusqueda(Model model) {
        return "lugar_de_encuentro";
    }

    @PostMapping("/IrSalon")
    public String irSalon(@RequestParam("nombres") String seleccion, Model model, HttpSession session) {
        List<String> nombres = lugarServicios.obtenerNombre();
        model.addAttribute("nombres", nombres);

        if (seleccion == null || seleccion.isBlank()) {
            model.addAttribute("mensaje", "Por favor selecciona una ruta válida.");
            return "buscar_ruta";
        }


        if (nombres.contains(seleccion)) {
            session.setAttribute("seleccion", seleccion);
            return "redirect:/irRuta";
        } else {
            model.addAttribute("mensaje", "Ese salón no existe.");
            return "lugar_de_encuentro";
        }
    }

}
