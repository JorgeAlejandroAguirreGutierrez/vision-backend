package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "correo_establecimiento")
@Data
@AllArgsConstructor
public class CorreoEstablecimiento extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "email", nullable = true)
    private String email;
	@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

	public CorreoEstablecimiento(long id){
		super(id);
	}
	public CorreoEstablecimiento() {
		super();
		this.codigo = Constantes.vacio;
		this.email = Constantes.vacio;
	}
}
