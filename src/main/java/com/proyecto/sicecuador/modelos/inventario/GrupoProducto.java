package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo_producto")
public class GrupoProducto extends Entidad {
	@JsonProperty("grupo")
    @Column(name = "grupo", nullable = true)
    private String grupo;
	
	@JsonProperty("subgrupo")
    @Column(name = "subgrupo", nullable = true)
    private String subgrupo;
	
	@JsonProperty("categoria")
    @Column(name = "categoria", nullable = true)
    private String categoria;
	
	@JsonProperty("linea")
    @Column(name = "linea", nullable = true)
    private String linea;
	
	@JsonProperty("sublinea")
    @Column(name = "sublinea", nullable = true)
    private String sublinea;
	
	@JsonProperty("presentacion")
    @Column(name = "presentacion", nullable = true)
    private String presentacion;

    public GrupoProducto(){
    	super();
    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String grupo, String subgrupo, String categoria, String linea, String sublinea, String presentacion){
        super(codigo);
        this.grupo=grupo;
        this.subgrupo=subgrupo;
        this.categoria=categoria;
        this.linea=linea;
        this.sublinea=sublinea;
        this.presentacion=presentacion;
        
    }

    public GrupoProducto(List<String>datos){

    }

    public String getGrupo() {
		return grupo;
	}
    
    public String getSubgrupo() {
		return subgrupo;
	}
    
    public String getCategoria() {
		return categoria;
	}
    
    public String getLinea() {
		return linea;
	}
    
    public String getSublinea() {
		return sublinea;
	}
    
    public String getPresentacion() {
		return presentacion;
	}
}
