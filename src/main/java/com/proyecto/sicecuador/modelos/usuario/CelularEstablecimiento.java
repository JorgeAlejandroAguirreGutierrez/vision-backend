package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "celular_establecimiento")
@Data
@AllArgsConstructor
public class CelularEstablecimiento extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
	@ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

	public CelularEstablecimiento(long id){
		super(id);
	}
	public CelularEstablecimiento() {
		super();
		this.codigo = Constantes.vacio;
		this.numero = Constantes.vacio;
	}
}
