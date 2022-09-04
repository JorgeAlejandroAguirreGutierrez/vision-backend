package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "grupo_producto")
public class GrupoProducto extends Entidad {
    @Column(name = "grupo", nullable = true)
    private String grupo;
    @Column(name = "subgrupo", nullable = true)
    private String subgrupo;
    @Column(name = "seccion", nullable = true)
    private String seccion;
    @Column(name = "linea", nullable = true)
    private String linea;
    @Column(name = "sublinea", nullable = true)
    private String sublinea;
    @Column(name = "presentacion", nullable = true)
    private String presentacion;
    
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id")
    private CategoriaProducto categoriaProducto;
    
    @ManyToOne
    @JoinColumn(name = "afectacion_contable_id")
    private AfectacionContable afectacionContable;
    
    public GrupoProducto(){
    	super();
    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion, CategoriaProducto categoriaProducto, AfectacionContable afectacionContable){
        super(codigo);
        this.grupo=grupo;
        this.subgrupo=subgrupo;
        this.seccion=seccion;
        this.linea=linea;
        this.sublinea=sublinea;
        this.presentacion=presentacion;
        this.categoriaProducto=categoriaProducto;
//        this.afectacionContable=afectacionContable;
        
    }

    public GrupoProducto(List<String>datos){

    }

    public String getGrupo() {
		return grupo;
	}
    
    public String getSubgrupo() {
		return subgrupo;
	}
    
    public String getSeccion() {
		return seccion;
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
    
    public CategoriaProducto getCategoriaProducto() {
    	return categoriaProducto;
    }

	public AfectacionContable getAfectacionContable() {
		return afectacionContable;
	}

	public void setAfectacionContable(AfectacionContable afectacionContable) {
		this.afectacionContable = afectacionContable;
	}
    
  
}
