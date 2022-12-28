package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "telefono_establecimiento")
public class TelefonoEstablecimiento extends Entidad {
	@Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;
	
	public TelefonoEstablecimiento() {
		super();
	}
	public TelefonoEstablecimiento(long id) {
		super(id);
	}
	
	public TelefonoEstablecimiento(String codigo, String numero, Establecimiento establecimiento) {
		super(codigo);
		this.numero=numero;
		this.establecimiento=establecimiento;
	}
    public TelefonoEstablecimiento(List<String>datos) {
        numero=datos.get(0)== null ? null: datos.get(0);
        establecimiento=datos.get(1)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(1)));
    }	
	public String getNumero() {
		return numero;
	}
	@JsonBackReference
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
