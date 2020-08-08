package com.proyecto.sicecuador.modelos.administracion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tecnico", nullable = true)
    private String nombre_tecnico;
    @Column(name = "endpoint", nullable = true)
    private String endpoint;
    public Modelo(){

    }

    public Modelo(long id) {
        super(id);
    }

    public Modelo(String nombre, String nombre_tecnico, String endpoint) {
        this.nombre = nombre;
        this.endpoint= endpoint;
        this.nombre_tecnico=nombre_tecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombre_tecnico() {
        return nombre_tecnico;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
