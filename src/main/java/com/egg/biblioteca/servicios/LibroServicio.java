package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.*;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {
    private void validar(Long isbn,String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        if (isbn == null || isbn==0) {
            throw new MiException("el isbn no puede ser nulo o 0");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacío");
        }
        if (ejemplares == null || ejemplares<0) {
            throw new MiException("los ejemplares no puede ser nulo o negativo");
        }
        if ( idAutor == null || idAutor.isEmpty()) {
            throw new MiException("el idAutor no puede ser nulo o estar vacío");
        }
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiException("el isbn no puede ser nulo o estar vacío");
        }
    }

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        validar(isbn,titulo,ejemplares,idAutor,idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(String titulo, Integer ejemplares, long isbn, String idAutor, String idEditorial) throws MiException {
        validar(isbn,titulo,ejemplares,idAutor,idEditorial);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);

        if (respuestaLibro.isPresent()) {
            Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
            if (respuestaEditorial.isPresent() && respuestaAutor.isPresent()) {
                Libro libro = respuestaLibro.get();
                Autor autor = respuestaAutor.get();
                Editorial editorial = respuestaEditorial.get();

                libro.setTitulo(titulo);
                libro.setEjemplares(ejemplares);
                libro.setAutor(autor);
                libro.setEditorial(editorial);

                libroRepositorio.save(libro);
            }
        }
    }
}
