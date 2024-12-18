package com.proyecto.vision.modelos.acceso;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_correo_establecimiento;

@Entity
@Table(name = tabla_correo_establecimiento)
@Getter
@Setter
@AllArgsConstructor
public class CorreoEstablecimiento extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "email", nullable = true)
    private String email;
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
