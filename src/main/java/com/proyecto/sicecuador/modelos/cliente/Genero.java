package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genero")
public class Genero extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("abreviatura")
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

    public Genero(List<String> datos){
        descripcion=datos.get(0)== null? null : datos.get(0);
        abreviatura=datos.get(1)== null? null : datos.get(1);
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
