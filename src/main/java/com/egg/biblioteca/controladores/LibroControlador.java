package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

@Controller
@RequestMapping("/libro") // localhost:8080/libro
@PreAuthorize("isAuthenticated()")  // Aplica a todos los métodos del controlador buscando evitar q alguien ingrese por URL
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")//Me aseguro que solo los usuario con Rol- ADMIN 
    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap model) {
        
        model.addAttribute("autores", autorServicio.listarAutores());
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "libro_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, 
                            @RequestParam(required = false) Integer ejemplares, 
                            @RequestParam String idAutor,
                            @RequestParam String idEditorial, ModelMap model) {
        try {
            if (idAutor == null || idEditorial == null) {
                throw new MiException("Debe seleccionar un autor y una editorial válidos.");
            }
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            model.put("exito", "El libro fue cargado correctamente.");
            

        } catch (MiException ex) {
            model.addAttribute("autores", autorServicio.listarAutores());
            model.addAttribute("editoriales", editorialServicio.listarEditoriales());
            model.put("error", ex.getMessage());

            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "inicio.html";
    }
    
    @GetMapping("lista")
    public String listar(ModelMap model) {
    
        model.addAttribute("libros", libroServicio.listarLibros());
        return "libro_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap model) {
        Libro libro = libroServicio.getOne(isbn);

        model.put("libro", libroServicio.getOne(isbn));
        model.addAttribute("autores", autorServicio.listarAutores());
        model.addAttribute("editoriales", editorialServicio.listarEditoriales()); 

        model.addAttribute("autorSeleccionado", libro.getAutor().getId());
        model.addAttribute("editorialSeleccionada", libro.getEditorial().getId());
        return "libro_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable @RequestParam(required = false) Long isbn, @RequestParam String titulo, 
    @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap model) {
        try {
            model.addAttribute("autores", autorServicio.listarAutores());
            model.addAttribute("editoriales", editorialServicio.listarEditoriales());
            if (idAutor == null || idEditorial == null) {
                throw new MiException("Debe seleccionar un autor y una editorial válidos.");
            }

            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

            return "redirect:../lista";

        } catch (MiException ex) {
            model.addAttribute("autores", autorServicio.listarAutores());
            model.addAttribute("editoriales", editorialServicio.listarEditoriales());
            model.put("error", ex.getMessage());
            return "libro_modificar.html";
        }
    }
}

