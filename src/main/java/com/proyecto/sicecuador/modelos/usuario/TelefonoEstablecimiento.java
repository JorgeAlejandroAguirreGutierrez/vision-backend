package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_telefono_establecimiento;

@Entity
@Table(name = tabla_telefono_establecimiento)
@Getter
@Setter
@AllArgsConstructor
public class TelefonoEstablecimiento extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

	public TelefonoEstablecimiento(long id){
		super(id);
	}

	public TelefonoEstablecimiento() {
		super();
		this.codigo = Constantes.vacio;
		this.numero = Constantes.vacio;
	}
}
