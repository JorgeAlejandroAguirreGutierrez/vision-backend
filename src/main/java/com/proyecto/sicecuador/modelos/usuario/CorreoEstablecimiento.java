package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "correo_establecimiento")
public class CorreoEstablecimiento extends Entidad {
    
	@Column(name = "email", nullable = true)
    private String email;
	@Column(name = "estado", nullable = true)
	private String estado;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;
	
	public CorreoEstablecimiento() {
		super();
	}
	public CorreoEstablecimiento(long id) {
		super(id);
	}
	
	public CorreoEstablecimiento(String codigo, String email, String estado, Establecimiento establecimiento) {
		super(codigo);
		this.email=email;
		this.estado=estado;
		this.establecimiento=establecimiento;
	}
    public CorreoEstablecimiento(List<String>datos) {
        email=datos.get(0)== null ? null: datos.get(0);
        estado=datos.get(1)== null ? null: datos.get(1);
        establecimiento=datos.get(2)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(2)));
    }	
	public String getEmail() {
		return email;
	}
	
	public String getEstado() {
		return estado;
	}
	@JsonBackReference
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	
	
}
