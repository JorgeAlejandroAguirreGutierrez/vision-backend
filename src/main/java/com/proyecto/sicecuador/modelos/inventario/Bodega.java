package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bodega")
public class Bodega extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "estado", nullable = true)
    private String estado;
    public Bodega(){
    }
    public Bodega(long id){
        super(id);
    }
    public Bodega(String codigo){
        super(codigo);
    }
    public Bodega(String codigo, String nombre, String estado){
        super(codigo);
        this.nombre=nombre;
        this.estado=estado;
    }

    public Bodega(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        nombre=datos.get(1)== null ? null: datos.get(1);
    }

    public String getNombre() {
		return nombre;
	}
    public void setNombre(String nombre) {
    	this.nombre=nombre;
    }
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
}
