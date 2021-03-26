package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "sub_grupo_producto")
public class SubGrupoProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JsonProperty("grupo_producto")
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupoProducto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("sub_grupo_producto")
    @JoinColumn(name = "sub_grupo_producto_id")
    private List<CategoriaProducto> categoriasProducto;

    public SubGrupoProducto(){

    }

    public SubGrupoProducto(long id){
        super(id);
    }

    public SubGrupoProducto(String codigo, String nombre, GrupoProducto grupoProducto){
        super(codigo);
        this.nombre=nombre;
        this.grupoProducto=grupoProducto;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public GrupoProducto getGrupoProducto() {
		return grupoProducto;
	}
    
    public void setGrupoProducto(GrupoProducto grupoProducto) {
		this.grupoProducto = grupoProducto;
	}
}
