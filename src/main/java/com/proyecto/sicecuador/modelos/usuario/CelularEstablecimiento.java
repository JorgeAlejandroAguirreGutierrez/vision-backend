package com.proyecto.vision.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_celular_establecimiento;

@Entity
@Table(name = tabla_celular_establecimiento)
@Getter
@Setter
@AllArgsConstructor
public class CelularEstablecimiento extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
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
