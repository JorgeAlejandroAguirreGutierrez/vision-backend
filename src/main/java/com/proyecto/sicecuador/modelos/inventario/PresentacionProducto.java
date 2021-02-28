package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.PresentacionProductoUtil;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "presentacion_producto")
@EntityListeners({PresentacionProductoUtil.class})
public class PresentacionProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JsonProperty("sub_linea_producto")
    @JoinColumn(name = "sub_linea_producto_id", nullable = true)
    private SubLineaProducto subLineaProducto;

    public PresentacionProducto(){

    }

    public PresentacionProducto(long id){
        super(id);
    }

    public PresentacionProducto(String codigo, String nombre, SubLineaProducto subLineaProducto){
        super(codigo);
        this.nombre=nombre;
        this.subLineaProducto=subLineaProducto;
    }
    
    public PresentacionProducto(List<String>datos) {
    	
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public SubLineaProducto getSubLineaProducto() {
		return subLineaProducto;
	}
    
    public void setSubLineaProducto(SubLineaProducto subLineaProducto) {
    	this.subLineaProducto = subLineaProducto;
    }
}
