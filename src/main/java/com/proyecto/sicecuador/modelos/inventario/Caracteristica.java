package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.FacturaCaracteristica;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.otros.inventario.CaracteristicaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristica")
@EntityListeners({CaracteristicaUtil.class})
public class Caracteristica extends Entidad {
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "color", nullable = true)
    private String color;
    @Column(name = "marca", nullable = true)
    private String marca;
    @Column(name = "modelo", nullable = true)
    private String modelo;
    @Column(name = "serie", nullable = true)
    private String serie;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bodega_producto_id", nullable = true)
    private BodegaProducto bodega_producto;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "caracteristica_id")
    private List<FacturaCaracteristica> factura_caracteristicas;

    public Caracteristica(){

    }

    public Caracteristica(long id){
        super(id);
    }

    public Caracteristica(String codigo, long cantidad, String descripcion, String color, String marca, String modelo, String serie, BodegaProducto bodega_producto) {
        super(codigo);
        this.cantidad=cantidad;
        this.descripcion = descripcion;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.bodega_producto=bodega_producto;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerie() {
        return serie;
    }
    @JsonBackReference
    public BodegaProducto getBodega_producto() {
        return bodega_producto;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
    /*
    public void setFactura_caracteristicas(List<FacturaCaracteristica> factura_caracteristicas) {
        this.factura_caracteristicas = factura_caracteristicas;
    }*/
    @JsonManagedReference(value="caracteristica-factura-caracteristica")
    public List<FacturaCaracteristica> getFactura_caracteristicas() {
        return factura_caracteristicas;
    }
}
