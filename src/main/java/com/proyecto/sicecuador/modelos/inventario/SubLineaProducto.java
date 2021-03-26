package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sub_linea_producto")
public class SubLineaProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JsonProperty("linea_producto")
    @JoinColumn(name = "linea_producto_id", nullable = true)
    private LineaProducto lineaProducto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("presentaciones_producto")
    @JoinColumn(name = "sub_linea_producto_id")
    private List<PresentacionProducto> presentacionesProducto;

    public SubLineaProducto(){

    }

    public SubLineaProducto(long id){
        super(id);
    }

    public SubLineaProducto(String codigo, String nombre, LineaProducto lineaProducto){
        super(codigo);
        this.nombre=nombre;
        this.lineaProducto=lineaProducto;
    }

    public SubLineaProducto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public LineaProducto getLineaProducto() {
		return lineaProducto;
	}
    public void setLineaProducto(LineaProducto lineaProducto) {
		this.lineaProducto = lineaProducto;
	}
}
