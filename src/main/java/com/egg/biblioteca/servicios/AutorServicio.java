package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);

        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {

        List<Autor> autores = new ArrayList<>();

        autores = autorRepositorio.findAll();
        return autores;
    }
    

    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException{    
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);

        } else {
            throw new MiException("No se encontró un autor con el ID especificado");
        }
    }
    // Posible método para eliminar autor. Actualmente no implementado en el proyecto
    // @Transactional
    // //Opción con UUID
    // // public void eliminar(UUID id) throws MiException{
    // public void eliminar(String id) throws MiException{
    //     Optional<Autor> autorOpt = autorRepositorio.findById(id);
    //     if (autorOpt.isPresent()) {
    //         autorRepositorio.delete(autorOpt.get());
    //     } else {
    //         throw new MiException("El autor con el ID especificado no existe");
    //     }

    // }

    // Metodo para recuperar un autor por ID
    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.findById(id).orElse(null);
    }
    
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
    }
    
}
