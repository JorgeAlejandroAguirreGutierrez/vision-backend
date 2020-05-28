package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;

import javax.persistence.*;

@Entity
@Table(name = "factura_caracteristica")
public class FacturaCaracteristica extends Entidad {
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "caracteristica_id", nullable = true)
    private Caracteristica caracteristica;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "factura_detalle_id", nullable = true)
    private FacturaDetalle factura_detalle;


    public FacturaCaracteristica(){

    }

    public FacturaCaracteristica(long id){
        super(id);
    }

    public FacturaCaracteristica(String codigo, long cantidad,Caracteristica caracteristica, FacturaDetalle factura_detalle) {
        super(codigo);
        this.cantidad=cantidad;
        this.caracteristica=caracteristica;
        this.factura_detalle=factura_detalle;
    }

    public long getCantidad() {
        return cantidad;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }
    @JsonBackReference(value="caracteristica-factura-caracteristica")
    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    @JsonBackReference(value="factura-detalle-factura-caracteristica")
    public void setFactura_detalle(FacturaDetalle factura_detalle) {
        this.factura_detalle = factura_detalle;
    }
}
