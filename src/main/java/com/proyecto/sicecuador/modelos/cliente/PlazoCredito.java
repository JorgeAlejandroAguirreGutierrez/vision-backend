package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plazo_credito")
public class PlazoCredito extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("plazo")
    @Column(name = "plazo", nullable = true)
    private double plazo;

    public PlazoCredito(){
        super();
    }

    public PlazoCredito(long id) {
        super(id);
    }

    public PlazoCredito(String codigo, String descripcion, double plazo){
        super(codigo);
        this.descripcion=descripcion;
        this.plazo=plazo;
    }

    public PlazoCredito(List<String> datos) {
        descripcion=datos.get(0)== null? null : datos.get(0);;
        plazo=datos.get(1)== null? null : Double.parseDouble(datos.get(1));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPlazo() {
        return plazo;
    }
}
