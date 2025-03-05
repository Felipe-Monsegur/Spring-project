package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")//localhost:8080/libro/registrar
    public String registrar(){
        return "libro_form.html";
    }

     @PostMapping("/registro")
  public String registro(
    @RequestParam(required = false) Long isbn, 
    @RequestParam String titulo, 
 	@RequestParam(required = false) Integer ejemplares, 
    @RequestParam String idAutor,
    @RequestParam String idEditorial) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
        } catch (MiException ex) {
            
            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "index.html";
    }
    
}
