package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;

import javax.persistence.*;

@Entity
@Table(name = "genero")
public class Genero extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Genero(){
        super();
    }

    public Genero(long id) {
        super(id);
    }

    public Genero(String codigo, String descripcion, String abreviatura) {
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
