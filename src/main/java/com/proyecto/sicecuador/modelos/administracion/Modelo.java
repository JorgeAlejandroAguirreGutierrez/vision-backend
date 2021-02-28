package com.proyecto.sicecuador.modelos.administracion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("nombre_tecnico")
    @Column(name = "nombre_tecnico", nullable = true)
    private String nombreTecnico;
	@JsonProperty("endpoint")
    @Column(name = "endpoint", nullable = true)
    private String endpoint;
    public Modelo(){

    }

    public Modelo(long id) {
        super(id);
    }

    public Modelo(String nombre, String nombreTecnico, String endpoint) {
        this.nombre = nombre;
        this.endpoint= endpoint;
        this.nombreTecnico=nombreTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
