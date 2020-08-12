package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.LineaProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "linea_producto")
@EntityListeners({LineaProductoUtil.class})
public class LineaProducto extends Entidad {
    @Column(name = "linea", nullable = true)
    private String linea;
    @Column(name = "sublinea", nullable = true)
    private String sublinea;
    @Column(name = "presentacion", nullable = true)
    private String presentacion;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupo_producto;

    public LineaProducto(){

    }

    public LineaProducto(long id){
        super(id);
    }

    public LineaProducto(String codigo, String linea, String sublinea, String presentacion, GrupoProducto grupo_producto){
        super(codigo);
        this.linea=linea;
        this.sublinea=sublinea;
        this.presentacion=presentacion;
        this.grupo_producto=grupo_producto;
    }

    public LineaProducto(List<String> datos){

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
    @JsonBackReference
    public GrupoProducto getGrupo_producto() {
        return grupo_producto;
    }
}
