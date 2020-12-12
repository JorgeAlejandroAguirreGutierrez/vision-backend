package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.LineaProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "linea_producto")
@EntityListeners({LineaProductoUtil.class})
public class LineaProducto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoria_producto;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "linea_producto_id")
    private List<SubLineaProducto> sub_lineas_productos;

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
    @JsonManagedReference
    public List<SubLineaProducto> getSub_lineas_productos() {
        return sub_lineas_productos;
    }
    @JsonBackReference
    public CategoriaProducto getCategoria_producto() {
        return categoria_producto;
    }
}
