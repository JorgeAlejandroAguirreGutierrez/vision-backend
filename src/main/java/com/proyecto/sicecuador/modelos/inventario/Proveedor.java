package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.ProveedorUtil;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
@EntityListeners({ProveedorUtil.class})
public class Proveedor extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    public Proveedor(long id){
        super(id);
    }
    public Proveedor(String codigo){
        super(codigo);
    }
    public Proveedor(){
        super();
    }

    public Proveedor(String codigo, String nombre){
        super(codigo);
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
