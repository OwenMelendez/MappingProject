package com.example.mapping.controller;

import com.example.mapping.entidad.Datos;
import com.example.mapping.servicio.DatosServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private DatosServicio datosServicio;

    @GetMapping({"/inicio", "/"})
    public String mostrarInicio() {
        return "inicio";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/inicio";
    }

    @PostMapping("/iniciar")
    public String comprobar(@RequestParam String email, @RequestParam String contraseña, Model model, HttpSession session) {


        if (email == null || email.trim().isEmpty() || contraseña == null || contraseña.trim().isEmpty()) {
            model.addAttribute("mensaje", "Todos los campos deben estar completos");
            return "inicio";
        }



        boolean credencialesValidas = datosServicio.verificarDatos(email, contraseña);

        if (credencialesValidas) {
            Datos usuario = datosServicio.obtenerPorEmail(email);
            session.setAttribute("rol", usuario.getRol());
            session.setAttribute("usuario", usuario);
            return "index";
        } else {
            model.addAttribute("mensaje", "Las credenciales no existen.");
            return "inicio";
        }
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam("id") String numId, @RequestParam String nombre,
                            @RequestParam String apellido, @RequestParam String email,
                            @RequestParam String contraseña, Model model, HttpSession session) {

        if (numId.isBlank() || nombre.isBlank() || apellido.isBlank() || email.isBlank() || contraseña.isBlank()) {
            model.addAttribute("mensaje", "Por favor, complete todos los campos.");
            return "registro";
        }

        try {
            long id = Long.parseLong(numId);
            if (datosServicio.existePorEmail(email)) {
                model.addAttribute("mensaje", "Este email ya está registrado.");
                return "registro";
            }
            if (datosServicio.existePorId(id)) {
                model.addAttribute("mensaje", "Este ID ya está registrado.");
                return "registro";
            }

            Datos datos = new Datos(id, nombre, apellido, email, contraseña, "Usuario");
            datosServicio.guardarDatos(datos);
            session.setAttribute("rol", datos.getRol());
            session.setAttribute("usuario", datos);

            model.addAttribute("mensaje", "Bienvenido a Mapping, te estábamos esperando.");
            return "index";

        } catch (NumberFormatException e) {
            model.addAttribute("mensaje", "El ID debe ser un número válido.");
            return "registro";
        }
    }


}
