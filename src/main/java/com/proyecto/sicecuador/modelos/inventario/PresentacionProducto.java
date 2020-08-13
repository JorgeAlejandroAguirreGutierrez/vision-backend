package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.PresentacionProductoUtil;

import javax.persistence.*;

@Entity
@Table(name = "presentacion_producto")
@EntityListeners({PresentacionProductoUtil.class})
public class PresentacionProducto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sub_linea_producto_id", nullable = true)
    private SubLineaProducto sub_linea_producto;

    public PresentacionProducto(){

    }

    public PresentacionProducto(long id){
        super(id);
    }

    public PresentacionProducto(String codigo, String nombre, SubLineaProducto sub_linea_producto){
        super(codigo);
        this.nombre=nombre;
        this.sub_linea_producto=sub_linea_producto;
    }

    public String getNombre() {
        return nombre;
    }
    @JsonBackReference
    public SubLineaProducto getSub_linea_producto() {
        return sub_linea_producto;
    }
}
