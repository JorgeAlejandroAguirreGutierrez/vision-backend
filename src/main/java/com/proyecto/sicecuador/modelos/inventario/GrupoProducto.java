package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo_producto")
public class GrupoProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("sub_grupos_productos")
    @JoinColumn(name = "grupo_producto_id")
    private List<SubGrupoProducto> subGruposProductos;

    public GrupoProducto(){

    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String nombre){
        super(codigo);
        this.nombre=nombre;
    }

    public GrupoProducto(List<String>datos){

    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
