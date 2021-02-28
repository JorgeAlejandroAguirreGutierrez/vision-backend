package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "precio")
public class Precio extends Entidad {
	@JsonProperty("costo")
    @Column(name = "costo", nullable = true)
    private double costo;
	@JsonProperty("margen_ganancia")
    @Column(name = "margen_ganancia", nullable = true)
    private double margenGanancia;
	@JsonProperty("precio_venta_publico")
    @Column(name = "precio_venta_publico", nullable = true)
    private double precioVentaPublico;
	@JsonProperty("precio_venta_publico_iva")
    @Column(name = "precio_venta_publico_iva", nullable = true)
    private double precioVentaPublicoIva;
	@JsonProperty("precio_venta_publico_manual")
    @Column(name = "precio_venta_publico_manual", nullable = true)
    private double precioVentaPublicoManual;
	@JsonProperty("utilidad")
    @Column(name = "utilidad", nullable = true)
    private double utilidad;
	@JsonProperty("utilidad_porcentaje")
    @Column(name = "utilidad_porcentaje", nullable = true)
    private double utilidadPorcentaje;

    @ManyToOne
    @JoinColumn(name = "medida_precio_id", nullable = true)
    private MedidaPrecio medidaPrecio;
    @ManyToOne
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
                  double precioVentaPublico, double precioVentaPublicoIva, double precioVentaPublicoManual,
                  double utilidad, double utilidadPocentaje, MedidaPrecio medidaPrecio, Segmento segmento){
        super(codigo);
        this.costo=costo;
        this.margenGanancia=margen_ganancia;
        this.precioVentaPublico=precioVentaPublico;
        this.precioVentaPublicoIva=precioVentaPublicoIva;
        this.precioVentaPublicoManual=precioVentaPublicoManual;
        this.utilidad=utilidad;
        this.utilidadPorcentaje=utilidadPocentaje;
        this.medidaPrecio=medidaPrecio;
        this.segmento=segmento;
    }

    public double getCosto() {
        return costo;
    }

    public double getMargenGanancia() {
		return margenGanancia;
	}

    public double getPrecioVentaPublico() {
		return precioVentaPublico;
	}

    public double getPrecioVentaPublicoIva() {
		return precioVentaPublicoIva;
	}

    public double getPrecioVentaPublicoManual() {
		return precioVentaPublicoManual;
	}

    public double getUtilidad() {
        return utilidad;
    }

    public double getUtilidadPorcentaje() {
		return utilidadPorcentaje;
	}

    public Segmento getSegmento() {
        return segmento;
    }

    @JsonBackReference
    public MedidaPrecio getMedidaPrecio() {
		return medidaPrecio;
	}
}
