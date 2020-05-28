package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.otros.inventario.CaracteristicaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristica")
@EntityListeners({CaracteristicaUtil.class})
public class Caracteristica extends Entidad {
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "factura_detalle_id", nullable = true)
    private FacturaDetalle factura_detalle;

    public Caracteristica(){

    }

    public Caracteristica(long id){
        super(id);
    }

    public Caracteristica(String codigo, String descripcion, String color, String marca, String modelo, String serie, BodegaProducto bodega_producto, FacturaDetalle factura_detalle) {
        super(codigo);
        this.descripcion = descripcion;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.bodega_producto=bodega_producto;
        this.factura_detalle=null;
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

    @JsonBackReference(value="factura-detalle-caracteristica")
    public void setFactura_detalle(FacturaDetalle factura_detalle) {
        this.factura_detalle = factura_detalle;
    }

    public FacturaDetalle getFactura_detalle() {
        return factura_detalle;
    }
}
