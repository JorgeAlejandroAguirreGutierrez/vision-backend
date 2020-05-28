package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.BodegaProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bodega_producto")
@EntityListeners({BodegaProductoUtil.class})
public class BodegaProducto extends Entidad {
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "kardex_id", nullable = true)
    private Kardex kardex;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "bodega_producto_id")
    private List<Caracteristica> caracteristicas;

    public BodegaProducto(){
        super();
    }
    public BodegaProducto(long id){
        super(id);
    }
    public BodegaProducto(String codigo){
        super(codigo);
    }
    public BodegaProducto(String codigo, Producto producto, Kardex kardex, Bodega bodega){
        super(codigo);
        this.producto=producto;
        this.kardex=kardex;
        this.bodega=bodega;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }
    public Kardex getKardex() {
        return kardex;
    }
    public Bodega getBodega() {
        return bodega;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }
    @JsonManagedReference
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }
}
