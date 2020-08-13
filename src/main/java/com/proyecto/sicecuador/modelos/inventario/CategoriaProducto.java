package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.CaracteristicaUtil;
import com.proyecto.sicecuador.otros.inventario.CategoriaProductoUtil;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "categoria_producto")
@EntityListeners({CategoriaProductoUtil.class})
public class CategoriaProducto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sub_grupo_producto_id", nullable = true)
    private SubGrupoProducto sub_grupo_producto;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria_producto_id")
    private List<LineaProducto> lineas_productos;

    public CategoriaProducto(){

    }

    public CategoriaProducto(long id){
        super(id);
    }

    public CategoriaProducto(String codigo, String nombre, SubGrupoProducto sub_grupo_producto){
        super(codigo);
        this.nombre=nombre;
        this.sub_grupo_producto=sub_grupo_producto;
    }

    public CategoriaProducto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
    @JsonManagedReference
    public List<LineaProducto> getLineas_productos() {
        return lineas_productos;
    }
    @JsonBackReference
    public SubGrupoProducto getSub_grupo_producto() {
        return sub_grupo_producto;
    }
}
