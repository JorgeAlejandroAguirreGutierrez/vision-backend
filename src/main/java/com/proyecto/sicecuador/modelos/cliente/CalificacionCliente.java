package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "calificacion_cliente")
public class CalificacionCliente extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
	
    public CalificacionCliente(){
        super();
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public CalificacionCliente(long id) {
        super(id);
    }

    public CalificacionCliente(String codigo, String descripcion, String abreviatura, String estado) {
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }

    public CalificacionCliente(List<String> datos){
        this.descripcion=datos.get(0)== null? null : datos.get(0);
        this.abreviatura=datos.get(1)== null? null : datos.get(1);
        this.estado=datos.get(2)== null? null : datos.get(2);
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
