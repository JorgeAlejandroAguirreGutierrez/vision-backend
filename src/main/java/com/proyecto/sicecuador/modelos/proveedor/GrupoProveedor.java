package com.proyecto.sicecuador.modelos.proveedor;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "grupo_proveedor")
public class GrupoProveedor extends Entidad {

	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	@Column(name = "abreviatura", nullable = true)
	private String abreviatura;
	@NotNull
	@NotEmpty
	@Column(name = "estado")
	private String estado;
	
	public GrupoProveedor(long id) {
		super(id);
	}
	
	public GrupoProveedor(String codigo, String descripcion, String abreviatura, String estado) {
		super(codigo);
		this.descripcion=descripcion;
		this.abreviatura=abreviatura;
		this.estado=estado;
	}
	
	public GrupoProveedor(List<String> datos) {
		descripcion=datos.get(0)== null? null : datos.get(0);
		abreviatura=datos.get(1)== null? null : datos.get(1);
		estado=datos.get(2)== null?null : datos.get(2);
	}

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    public String getEstado() {
        return estado;
    }
    
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

	
}