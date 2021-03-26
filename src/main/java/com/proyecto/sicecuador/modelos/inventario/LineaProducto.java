package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "linea_producto")
public class LineaProducto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JsonProperty("categoria_producto")
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoria_producto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("sub_lineas_producto")
    @JoinColumn(name = "linea_producto_id")
    private List<SubLineaProducto> subLineasProducto;

    public LineaProducto(){

    }

    public LineaProducto(long id){
        super(id);
    }

    public LineaProducto(String codigo, String nombre, CategoriaProducto categoria_producto){
        super(codigo);
        this.nombre=nombre;
        this.categoria_producto=categoria_producto;
    }

    public LineaProducto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public CategoriaProducto getCategoria_producto() {
        return categoria_producto;
    }
    
    public void setCategoria_producto(CategoriaProducto categoria_producto) {
		this.categoria_producto = categoria_producto;
	}
}
