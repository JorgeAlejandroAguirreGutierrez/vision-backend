package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bodega")
public class Bodega extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    public Bodega(){
    }
    public Bodega(long id){
        super(id);
    }
    public Bodega(String codigo){
        super(codigo);
    }
    public Bodega(String codigo, String nombre){
        super(codigo);
        this.nombre=nombre;
    }

    public Bodega(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
    }

    public String getNombre() {
		return nombre;
	}
}
