package com.egg.biblioteca.entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String mime;
    
    private String nombre;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    //CON LOMBOK YA NO NECESITAMOS DECLARAR LOS GETTERS, SETTERS O CONSTRUCTORES
}

