package com.egg.biblioteca.entidades;

// import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@toString
@NoArgsConstructor
@Entity
public class Autor {
    // Descomentar si usas UUID
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private UUID id;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nombre;

    //CON LOMBOK YA NO NECESITAMOS DECLARAR LOS GETTERS, SETTERS O CONSTRUCTORES

    // public Autor() {
    // }
    // public String getId() {
    //         return id;
    //     }

    // public void setId(String id) {
    //     this.id = id;
    // }
    // // Descomentar si usas UUID
    // // public UUID getId() {
    // //     return id;
    // // }

    // // public void setId(UUID id) {
    // //     this.id = id;
    // // }

    // public String getNombre() {
    //     return nombre;
    // }

    // public void setNombre(String nombre) {
    //     this.nombre = nombre;
    // }

    // @Override
    // public String toString() {
    //     return "Autor [id=" + id + ", nombre=" + nombre + "]";
    // }

    

    

}
