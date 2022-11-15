package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "estacion_usuario")
public class EstacionUsuario extends Entidad {
    
	@Column(name = "estado", nullable = true)
	private String estado;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
	
	public EstacionUsuario() {
		super();
	}
	public EstacionUsuario(long id) {
		super(id);
	}
	
	public EstacionUsuario(String codigo, String estado, Usuario usuario, Estacion estacion) {
		super(codigo);	
		this.estado=estado;
		this.usuario=usuario;
		this.estacion=estacion;
	}
    public EstacionUsuario(List<String>datos) {
        estado=datos.get(0)== null ? null: datos.get(0);
        usuario=datos.get(1)== null ? null:new Usuario((long) Double.parseDouble(datos.get(1)));
        estacion=datos.get(2)== null ? null:new Estacion((long) Double.parseDouble(datos.get(2)));        
    }	
  
	public String getEstado() {
		return estado;
	}
	
	public Estacion getEstacion() {
		return estacion;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@JsonBackReference
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	
	
}
