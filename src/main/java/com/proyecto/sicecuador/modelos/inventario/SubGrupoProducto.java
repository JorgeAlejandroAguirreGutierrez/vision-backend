package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.SubGrupoProductoUtil;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "sub_grupo_producto")
@EntityListeners({SubGrupoProductoUtil.class})
public class SubGrupoProducto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupo_producto;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sub_grupo_producto_id")
    private List<CategoriaProducto> categorias_productos;

    public SubGrupoProducto(){

    }

    public SubGrupoProducto(long id){
        super(id);
    }

    public SubGrupoProducto(String codigo, String nombre, GrupoProducto grupo_producto){
        super(codigo);
        this.nombre=nombre;
        this.grupo_producto=grupo_producto;
    }

    public String getNombre() {
        return nombre;
    }
    @JsonManagedReference
    public List<CategoriaProducto> getCategorias_productos() {
        return categorias_productos;
    }
    @JsonBackReference
    public GrupoProducto getGrupo_producto() {
        return grupo_producto;
    }
}
