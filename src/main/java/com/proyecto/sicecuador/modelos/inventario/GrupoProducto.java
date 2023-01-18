package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;

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
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id")
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JoinColumn(name = "cuenta_contable_id", nullable = true)
    private CuentaContable cuentaContable;
    
    public GrupoProducto(){
    	super();
    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion, String estado, CategoriaProducto categoriaProducto, CuentaContable cuentaContable){
        super(codigo);
        this.grupo=grupo;
        this.subgrupo=subgrupo;
        this.seccion=seccion;
        this.linea=linea;
        this.sublinea=sublinea;
        this.presentacion=presentacion;
        this.estado=estado;
        this.categoriaProducto=categoriaProducto;
        this.cuentaContable=cuentaContable;
        
    }

    public GrupoProducto(List<String>datos){
    	grupo=datos.get(0)== null ? null: datos.get(0);
    	subgrupo=datos.get(1)== null ? null: datos.get(1);
    	seccion=datos.get(1)== null ? null: datos.get(1);
    	linea=datos.get(1)== null ? null: datos.get(1);
    	sublinea=datos.get(1)== null ? null: datos.get(1);
    	presentacion=datos.get(1)== null ? null: datos.get(1);
    	estado=datos.get(1)== null ? null: datos.get(1);
    	categoriaProducto=datos.get(0)==null ? null: new CategoriaProducto((long) Double.parseDouble(datos.get(0)));
    	cuentaContable=datos.get(0)==null ? null: new CuentaContable((long) Double.parseDouble(datos.get(0)));

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

    public String getEstado() {
        return estado;
    }

    public CategoriaProducto getCategoriaProducto() {
    	return categoriaProducto;
    }
    
    public CuentaContable getCuentaContable() {
		return cuentaContable;
	}
    
    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}
    
    public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
