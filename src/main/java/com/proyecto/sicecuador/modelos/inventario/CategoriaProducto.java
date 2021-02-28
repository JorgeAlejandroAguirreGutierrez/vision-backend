package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.CategoriaProductoUtil;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria_producto")
@EntityListeners({CategoriaProductoUtil.class})
public class CategoriaProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JsonProperty("sub_grupo_producto")
    @JoinColumn(name = "sub_grupo_producto_id", nullable = true)
    private SubGrupoProducto subGrupoProducto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("lineas_producto")
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private List<LineaProducto> lineasProducto;

    public CategoriaProducto(){

    }

    public CategoriaProducto(long id){
        super(id);
    }

    public CategoriaProducto(String codigo, String nombre, SubGrupoProducto subGrupoProducto){
        super(codigo);
        this.nombre=nombre;
        this.subGrupoProducto=subGrupoProducto;
    }

    public CategoriaProducto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

   public SubGrupoProducto getSubGrupoProducto() {
	   return subGrupoProducto;
   }
    
    public void setSubGrupoProducto(SubGrupoProducto subGrupoProducto) {
		this.subGrupoProducto = subGrupoProducto;
	}
}
