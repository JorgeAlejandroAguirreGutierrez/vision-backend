package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "segmento")
public class Segmento extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	
	@JsonProperty("margen_ganancia")
    @Column(name = "margen_ganancia", nullable = true)
    private double margenGanancia;
	
    public Segmento(){
    }
    public Segmento(long id){
        super(id);
    }
    public Segmento(String codigo){
        super(codigo);
    }
    public Segmento(String codigo, String nombre, double margenGanancia){
        super(codigo);
        this.nombre=nombre;
        this.margenGanancia=margenGanancia;
    }

    public Segmento(List<String>datos){
    	nombre=datos.get(0)==null? null : datos.get(0);
    	//margenGanancia=datos.get(1)==null? null : datos.get(1);
    }

    public String getNombre() {
        return nombre;
    }

    public double getMargenGanancia() {
        return margenGanancia;
    }

    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public void setMargenGanancia(double margenGanancia) {
		this.margenGanancia = margenGanancia;
	}
}
