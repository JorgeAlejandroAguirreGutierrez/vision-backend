package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.PrecioUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "precio")
@EntityListeners({PrecioUtil.class})
public class Precio extends Entidad {
    @Column(name = "costo", nullable = true)
    private double costo;
    @Column(name = "margen_contribucion", nullable = true)
    private double margen_contribucion;
    @Column(name = "precio_venta_publico", nullable = true)
    private double precio_venta_publico;
    @Column(name = "precio_venta_publico_iva", nullable = true)
    private double precio_venta_publico_iva;
    @Column(name = "utilidad", nullable = true)
    private double utilidad;
    @Column(name = "utilidad_porcentaje", nullable = true)
    private double utilidad_porcentaje;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    @ManyToOne(cascade = CascadeType.MERGE)
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
    public Precio(String codigo, double costo, double margen_contribucion,
                  double precio_venta_publico, double precio_venta_publico_iva,
                  double utilidad, double utilidad_pocentaje, Medida medida, Segmento segmento){
        super(codigo);
        this.costo=costo;
        this.margen_contribucion=margen_contribucion;
        this.precio_venta_publico=precio_venta_publico;
        this.precio_venta_publico_iva=precio_venta_publico_iva;
        this.utilidad=utilidad;
        this.utilidad_porcentaje=utilidad_pocentaje;
        this.medida=medida;
        this.segmento=segmento;
    }

    public double getCosto() {
        return costo;
    }

    public double getMargen_contribucion() {
        return margen_contribucion;
    }

    public double getPrecio_venta_publico() {
        return precio_venta_publico;
    }

    public double getPrecio_venta_publico_iva() {
        return precio_venta_publico_iva;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public double getUtilidad_porcentaje() {
        return utilidad_porcentaje;
    }
}
