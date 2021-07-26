package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "calificacion_cliente")
public class CalificacionCliente extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @NotNull
    @NotBlank
    @JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@NotNull
	@NotEmpty
	@JsonProperty("estado")
    @Column(name = "estado")
    private String estado;
	
    public CalificacionCliente(){

    }

    public CalificacionCliente(long id) {
        super(id);
    }

    public CalificacionCliente(String codigo, String descripcion, String abreviatura, String estado) {
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }

    public CalificacionCliente(List<String> datos){
        this.descripcion=datos.get(0)== null? null : datos.get(0);
        this.abreviatura=datos.get(1)== null? null : datos.get(1);
        this.estado=datos.get(2)== null? null : datos.get(2);
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
