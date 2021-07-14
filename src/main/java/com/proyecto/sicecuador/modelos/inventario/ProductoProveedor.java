package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto_proveedor")
public class ProductoProveedor extends Entidad {
    @ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JsonProperty("proveedor")
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public ProductoProveedor(){
        super();
    }

    public ProductoProveedor(long id){
        super(id);
    }

    public ProductoProveedor(String codigo, Producto producto, Proveedor proveedor){
        super(codigo);
        this.producto=producto;
        this.proveedor=proveedor;
    }
    
    public ProductoProveedor(List<String> datos){
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }
}
