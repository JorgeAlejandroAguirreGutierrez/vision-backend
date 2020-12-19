package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.SubLineaProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sub_linea_producto")
@EntityListeners({SubLineaProductoUtil.class})
public class SubLineaProducto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "linea_producto_id", nullable = true)
    private LineaProducto linea_producto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "sub_linea_producto_id")
    private List<PresentacionProducto> presentaciones_productos;

    public SubLineaProducto(){

    }

    public SubLineaProducto(long id){
        super(id);
    }

    public SubLineaProducto(String codigo, String nombre, LineaProducto linea_producto){
        super(codigo);
        this.nombre=nombre;
        this.linea_producto=linea_producto;
    }

    public SubLineaProducto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    public LineaProducto getLinea_producto() {
        return linea_producto;
    }
    public void setLinea_producto(LineaProducto linea_producto) {
		this.linea_producto = linea_producto;
	}
}
