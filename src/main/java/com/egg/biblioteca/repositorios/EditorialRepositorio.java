
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Editorial;

// import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

}

//Opción con UUID
// @Repository
// public interface EditorialRepositorio extends JpaRepository<Editorial, UUID> {

// }
