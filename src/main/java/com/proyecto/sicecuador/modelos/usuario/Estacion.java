package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "estacion")
public class Estacion extends Entidad {
	@NotNull
	@Column(name ="codigo_sri", nullable = true)
	private String codigoSri;
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
	@Column(name = "estado", nullable = true)
    private String estado;
	@ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public Estacion(){
        super();
    }

    public Estacion(long id) {
        super(id);
    }
    public Estacion(String codigo, String codigoSri, String descripcion, String estado, Establecimiento establecimiento){
        super(codigo);
        this.codigoSri=codigoSri;
        this.descripcion=descripcion;
        this.establecimiento=establecimiento;
        this.estado=estado;
    }
    public Estacion(List<String> datos){
    	codigoSri=datos.get(0)==null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
        estado=datos.get(1)== null ? null: datos.get(2);
    }
    public String getCodigoSri() {
    	return codigoSri;
    }
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getEstado() {
		return estado;
	}
    
    public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
