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
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;
	
	public CorreoEstablecimiento() {
		super();
	}
	
	public CorreoEstablecimiento(long id) {
		super(id);
	}
	
	public CorreoEstablecimiento(String codigo, String email, Establecimiento establecimiento) {
		super(codigo);
		this.email=email;
		this.establecimiento=establecimiento;
	}
    public CorreoEstablecimiento(List<String>datos) {
        email=datos.get(0)== null ? null: datos.get(0);
        establecimiento=datos.get(1)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(1)));
    }	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonBackReference
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
}
