package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria_producto")
//@EntityListeners({TipoProductoUtil.class})
public class CategoriaProducto extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("categoria")
    @Column(name = "categoria", nullable = true)
    private String categoria;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public CategoriaProducto(){
        super();
    }

    public CategoriaProducto(long id){
        super(id);
    }

    public CategoriaProducto(List<String> datos){

    }
    public CategoriaProducto(String codigo, String descripcion, String categoria, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.categoria=categoria;
        this.abreviatura=abreviatura;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
