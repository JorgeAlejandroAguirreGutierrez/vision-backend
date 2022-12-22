package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "forma_pago")
public class FormaPago extends Entidad {
	@NotNull
	@NotEmpty
    @Column(name = "codigo_sri")
    private String codigoSRI;    
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@NotNull
	@NotEmpty
    @Column(name = "estado")
    private String estado;
	
    public FormaPago(){
        super();
    }

    public FormaPago(long id) {
        super(id);
    }

    public FormaPago(String codigo, String codigoSRI, String descripcion, String abreviatura, String estado){
        super(codigo);
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }

    public FormaPago(List<String> datos) {
    	codigoSRI=datos.get(0)== null? null : datos.get(0);
        descripcion=datos.get(1)== null? null : datos.get(1);
        abreviatura=datos.get(2)== null? null : datos.get(2);
        estado=datos.get(3)== null? null : datos.get(3);
    }

    public String getCodigoSRI() {
		return codigoSRI;
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
