package com.proyecto.sicecuador.modelos.proveedor;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "grupo_proveedor")
@Data
@AllArgsConstructor
public class GrupoProveedor extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	@Column(name = "abreviatura", nullable = true)
	private String abreviatura;
	@Column(name = "estado", nullable = true)
	private String estado;

	public GrupoProveedor(long id){
		super(id);
	}
	public GrupoProveedor() {
		super();
		this.codigo = Constantes.vacio;
		this.descripcion = Constantes.vacio;
		this.abreviatura = Constantes.vacio;
		this.estado = Constantes.vacio;
	}
}
