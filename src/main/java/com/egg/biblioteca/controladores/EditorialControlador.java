package com.egg.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial") // localhost:8080/editorial
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio; // Inyección de dependencia

    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelMap) {
        try {
            editorialServicio.crearEditorial(nombre); // llamo a mi servicio para persistir
            modelMap.addAttribute("exito", "Editorial creado con éxito!");
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("error", "Ocurrió un error");
            return "editorial_form.html";
        }
        return "index.html";

    }
}
