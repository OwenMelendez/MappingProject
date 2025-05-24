package com.example.mapping.controller;


import com.example.mapping.servicio.LugarServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class BackController {

    @Autowired
    private LugarServicios lugarServicios;

    @GetMapping("/BackIndex")
    public String volver_index(){

        return "index";

    }

    @GetMapping("/BackBusqueda")
    public String volver_origen(){
        return "lugar_de_encuentro";
    }


}
