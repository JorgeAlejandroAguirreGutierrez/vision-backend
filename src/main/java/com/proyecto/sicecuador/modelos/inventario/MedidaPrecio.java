package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medida_precio")
public class MedidaPrecio extends Entidad {
    @ManyToOne
    @JsonProperty("medida")
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    @ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
/*    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("precios")
    @JoinColumn(name = "medida_precio_id", nullable = true)*/
    
    private List<Precio> precios;

    public MedidaPrecio(){
        super();
    }

    public MedidaPrecio(long id){
        super(id);
    }

    public MedidaPrecio(String codigo, Medida medida, Producto producto){
        super(codigo);
        this.medida=medida;
        this.producto=producto;
    }
    public MedidaPrecio(List<String> datos){
    }

    public Medida getMedida() {
        return medida;
    }

    @JsonManagedReference
    public List<Precio> getPrecios() {
        return precios;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }
}
