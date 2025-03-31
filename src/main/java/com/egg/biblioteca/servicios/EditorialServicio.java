package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {
    // Inyecta una instancia del repositorio de Editorial gestionada por Spring    
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    //Método para crear una Editorial
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre); //llamo al método validar que asegurará que el nombre no es nulo ni está vacío
        Editorial editorial = new Editorial(); // Instancio un objeto del tipo Editorial
        editorial.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro

        editorialRepositorio.save(editorial);// Persisto el dato en mi BBDD
    }

    // Metodo para recuperar una "lista de editoriales"
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        
        List<Editorial> editoriales = new ArrayList<>();

        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
    
    //Método para modificar editorial
    public void modificarEditorial(String id, String nombre) throws MiException {
        validar(nombre);
    
        if (id == null || id.isEmpty()) {
            throw new MiException("El ID de la editorial no puede ser nulo o vacío.");
        }
    
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
    
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
    
            try {
                editorialRepositorio.save(editorial);
            } catch (Exception e) {
                throw new MiException("Error al guardar la editorial: " + e.getMessage());
            }
        } else {
            throw new MiException("No se encontró una editorial con el ID especificado.");
        }
    }
    
    // Posible método para eliminar autor. Actualmente no implementado en el proyecto
    // @Transactional
    // //Opción con UUID
    // // public void eliminar(UUID id) throws MiException{
    // public void eliminar(String id) throws MiException{
    //     Optional<Editorial> editorialOpt = editorialRepositorio.findById(id);
    //     if (editorialOpt.isPresent()) {
    //         editorialRepositorio.delete(editorialOpt.get());
    //     } else {
    //         throw new MiException("La editorial con el ID especificado no existe");
    //     }
    // }

    // Método para recuperar una editorial por su ID
    @Transactional(readOnly = true)
    public Editorial getOne(String id) {
        return editorialRepositorio.findById(id).orElse(null);
    }

    // Método para validar que el nombre no sea nulo ni vacío
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El nombre de la editorial no puede ser nulo o estar vacío");
        }
    }
}
