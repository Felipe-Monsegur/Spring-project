package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
// import java.util.UUID;

@Service
public class LibroServicio {
    // Inyecta una instancia del repositorio de Libro gestionada por Spring    
    @Autowired
    private LibroRepositorio libroRepositorio;
    // Inyecta una instancia del repositorio de Autor gestionada por Spring    
    @Autowired
    private AutorRepositorio autorRepositorio;
    // Inyecta una instancia del repositorio de Editorial gestionada por Spring    
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    //Método para crear un Libro
    @Transactional
    //Opción con UUID
    // public void crearLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiException {
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(idEditorial);

        // Verificar que el autor y la editorial existan antes de asignarlos
        if (autorOptional.isEmpty()) {
            throw new MiException("Debe seleccionar un autor válido.");
        }

        if (editorialOptional.isEmpty()) {
            throw new MiException("Debe seleccionar una editorial válida.");
        }

        // Crear el objeto Libro y asignar los valores
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autorOptional.get()); // Se obtiene el objeto Autor de Optional
        libro.setEditorial(editorialOptional.get()); // Se obtiene el objeto Editorial de Optional

        // Persistir en la base de datos
        libroRepositorio.save(libro);
    }

    //Método para recuperar una "lista de libros"
    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();
        return libros;
    }

    //Método para modificar libro
    @Transactional
    //Opción con UUID
    // public void modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiException {
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        if (respuesta.isEmpty()) {
            throw new MiException("El libro especificado no existe.");
        }

        if (respuestaAutor.isEmpty()) {
            throw new MiException("El autor especificado no existe.");
        }

        if (respuestaEditorial.isEmpty()) {
            throw new MiException("La editorial especificada no existe.");
        }

        Libro libro = respuesta.get();
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(respuestaAutor.get());
        libro.setEditorial(respuestaEditorial.get());

        libroRepositorio.save(libro);
    }

    //Método para recuperar un libro por su ID
    @Transactional(readOnly = true)
    public Libro getOne(Long isbn) {
        return libroRepositorio.findById(isbn).orElse(null);
    }

    //Método para validar los atributos
    //Opción con UUID
    // private void validar(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiException {
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        if (isbn == null) {
            throw new MiException("El ISBN no puede ser nulo.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MiException("El título no puede ser nulo o estar vacío.");
        }
        if (ejemplares == null) {
            throw new MiException("La cantidad de ejemplares no puede ser nula.");
        }
        if (idAutor == null) {
            throw new MiException("El ID del autor no puede ser nulo o estar vacío.");
        }
        if (idEditorial == null) {
            throw new MiException("El ID de la editorial no puede ser nulo o estar vacío.");
        }
    }

}
