package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;

@Entity
@Table(name = "producto_bodega")
public class ProductoBodega extends Entidad {
    
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    
    @ManyToOne
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

    public Producto getProducto() {
        return producto;
    }
}
