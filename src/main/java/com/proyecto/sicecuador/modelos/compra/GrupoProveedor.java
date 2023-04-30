package com.proyecto.sicecuador.modelos.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_grupo_proveedor;

@Entity
@Table(name = tabla_grupo_proveedor)
@Getter
@Setter
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
	@ManyToOne
	@JoinColumn(name = "cuenta_contable_id", nullable = true)
	private CuentaContable cuentaContable;
	public GrupoProveedor() {
		super();
		this.codigo = Constantes.vacio;
		this.descripcion = Constantes.vacio;
		this.abreviatura = Constantes.vacio;
		this.estado = Constantes.vacio;
	}
	
	public GrupoProveedor(long id) {
		super(id);
	}
	public void normalizar(){
		if(this.cuentaContable == null) this.cuentaContable = new CuentaContable();
	}
}
