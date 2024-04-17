package com.proyecto.vision.modelos.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.acceso.Empresa;
import lombok.AllArgsConstructor;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_grupo_proveedor;

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
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
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
