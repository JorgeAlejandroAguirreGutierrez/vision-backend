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
    public Modelo(){

    }

    public Modelo(long id) {
        super(id);
    }

    public Modelo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
