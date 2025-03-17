package com.egg.biblioteca.controladores;

import java.util.List;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import java.util.logging.Level;
// import java.util.logging.Logger;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/autor") // localhost:8080/autor
public class AutorControlador {

    @Autowired 
    private AutorServicio autorServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Nos aseguramos que sin este ROL nadie pueda registrar. Ni usando la URL
    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Nos aseguramos que sin este ROL nadie pueda registrar. Ni usando la URL
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre);    // llamo a mi servicio para persistir
            modelo.put("exito", "El autor fue cargado correctamente.");       
        } catch (MiException ex) {    
            modelo.put("error", ex.getMessage());      
            return "autor_form.html";
        }        
        return "inicio.html";
    
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Nos aseguramos que sin este ROL nadie pueda registrar. Ni usando la URL
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        model.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Nos aseguramos que sin este ROL nadie pueda registrar. Ni usando la URL
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model) {
        try{
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        }catch (MiException ex) {
            model.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
        
    }
}

