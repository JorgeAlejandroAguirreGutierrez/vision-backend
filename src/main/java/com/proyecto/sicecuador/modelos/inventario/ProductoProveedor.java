package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import javax.persistence.*;

@Entity
@Table(name = "producto_proveedor")
public class ProductoProveedor extends Entidad {
    
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
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
   
    

    public Proveedor getProveedor() {
        return proveedor;
    }

 /*   public Producto getProducto() {
        return producto;
    }*/
}
