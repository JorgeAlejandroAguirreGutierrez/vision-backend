package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "grupo_cliente")
public class GrupoCliente extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@NotNull
	@NotEmpty
    @Column(name = "estado")
    private String estado;
	@ManyToOne
    @JoinColumn(name = "cuenta_contable_id", nullable = true)
    private CuentaContable cuentaContable;
	
    public GrupoCliente(){
        super();
    }

    public GrupoCliente(long id) {
        super(id);
    }

    public GrupoCliente(String codigo, String descripcion, String abreviatura, String estado, CuentaContable cuentaContable){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
        this.cuentaContable=cuentaContable;
    }

    public GrupoCliente(List<String> datos){
        descripcion=datos.get(0)== null? null : datos.get(0);
        abreviatura=datos.get(1)== null? null : datos.get(1);
        estado=datos.get(2)== null? null : datos.get(2);
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
    public CuentaContable getCuentaContable() {
		return cuentaContable;
	}
}
