package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto_bodega")
public class ProductoBodega extends Entidad {
    
	@JsonBackReference
	@ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JsonProperty("bodega")
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;

    public ProductoBodega(){
        super();
    }

    public ProductoBodega(long id){
        super(id);
    }

    public ProductoBodega(String codigo, Producto producto, Bodega bodega){
        super(codigo);
        this.producto=producto;
        this.bodega=bodega;
    }
   
    
    public Bodega getBodega() {
        return bodega;
    }
    public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

 /*   public Producto getProducto() {
        return producto;
    }*/
}
