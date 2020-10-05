package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "precio")
public class Precio extends Entidad {
    @Column(name = "costo", nullable = true)
    private double costo;
    @Column(name = "margen_ganancia", nullable = true)
    private double margen_ganancia;
    @Column(name = "precio_venta_publico", nullable = true)
    private double precio_venta_publico;
    @Column(name = "precio_venta_publico_iva", nullable = true)
    private double precio_venta_publico_iva;
    @Column(name = "precio_venta_publico_manual", nullable = true)
    private double precio_venta_publico_manual;
    @Column(name = "utilidad", nullable = true)
    private double utilidad;
    @Column(name = "utilidad_porcentaje", nullable = true)
    private double utilidad_porcentaje;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "medida_precio_id", nullable = true)
    private MedidaPrecio medida_precio;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "segmento_id", nullable = true)
    private Segmento segmento;

    public Precio(){
        super();
    }

    public Precio(long id){
        super(id);
    }

    public Precio(List<String> datos){

    }

    public Precio(String codigo, double costo, double margen_ganancia,
                  double precio_venta_publico, double precio_venta_publico_iva, double precio_venta_publico_manual,
                  double utilidad, double utilidad_pocentaje, MedidaPrecio medida_precio, Segmento segmento){
        super(codigo);
        this.costo=costo;
        this.margen_ganancia=margen_ganancia;
        this.precio_venta_publico=precio_venta_publico;
        this.precio_venta_publico_iva=precio_venta_publico_iva;
        this.precio_venta_publico_manual=precio_venta_publico_manual;
        this.utilidad=utilidad;
        this.utilidad_porcentaje=utilidad_pocentaje;
        this.medida_precio=medida_precio;
        this.segmento=segmento;
    }

    public double getCosto() {
        return costo;
    }

    public double getMargen_ganancia() {
        return margen_ganancia;
    }

    public double getPrecio_venta_publico() {
        return precio_venta_publico;
    }

    public double getPrecio_venta_publico_iva() {
        return precio_venta_publico_iva;
    }

    public double getPrecio_venta_publico_manual() {
        return precio_venta_publico_manual;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public double getUtilidad_porcentaje() {
        return utilidad_porcentaje;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    @JsonBackReference
    public MedidaPrecio getMedida_precio() {
        return medida_precio;
    }
}
