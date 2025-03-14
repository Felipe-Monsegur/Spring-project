package com.egg.biblioteca.controladores;


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

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial") // localhost:8080/editorial
@PreAuthorize("isAuthenticated()")  // Aplica a todos los métodos del controlador buscando evitar q alguien ingrese por URL
public class EditorialControlador {
    
    @Autowired 
    private EditorialServicio editorialServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Me aseguro que solo los usuario con Rol- ADMIN 
    @GetMapping("/registrar") // localhost:8080/editorial/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Me aseguro que solo los usuario con Rol- ADMIN
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre); 
            modelo.put("exito", "La editorial fue cargada correctamente.");       
        } catch (MiException ex) {          
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }        
        return "inicio.html";
    
    }

    @GetMapping("lista")
    public String listar(ModelMap model) {
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "editorial_list.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")/
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        model.put("editorial", editorialServicio.getOne(id));
        return "editorial_modificar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model) {
        try{
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../lista";
        }catch (MiException ex) {
            model.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
        
    }
}

