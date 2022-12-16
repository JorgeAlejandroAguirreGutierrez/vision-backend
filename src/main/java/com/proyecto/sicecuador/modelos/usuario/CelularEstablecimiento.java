package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "celular_establecimiento")
public class CelularEstablecimiento extends Entidad {
    
	@Column(name = "numero", nullable = true)
    private String numero;
	@Column(name = "estado", nullable = true)
	private String estado;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;
	
	public CelularEstablecimiento() {
		super();
	}
	public CelularEstablecimiento(long id) {
		super(id);
	}
	
	public CelularEstablecimiento(String codigo, String numero, String estado, Establecimiento establecimiento) {
		super(codigo);
		this.numero=numero;
		this.estado=estado;
		this.establecimiento=establecimiento;
	}
    public CelularEstablecimiento(List<String>datos) {
        numero=datos.get(0)== null ? null: datos.get(0);
        estado=datos.get(1)== null ? null: datos.get(1);
        establecimiento=datos.get(2)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(2)));
    }	
	public String getNumero() {
		return numero;
	}
	
	public String getEstado() {
		return estado;
	}
	
	@JsonBackReference
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
