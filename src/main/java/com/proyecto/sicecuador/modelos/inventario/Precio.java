package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "precio")
public class Precio extends Entidad {
    @Column(name = "costo", nullable = true)
    private double costo;
    @Column(name = "margen_ganancia", nullable = true)
    private double margenGanancia;
    @Column(name = "precio_venta_publico", nullable = true)
    private double precioVentaPublico;
    @Column(name = "precio_venta_publico_iva", nullable = true)
    private double precioVentaPublicoIva;
    @Column(name = "precio_sin_iva", nullable = true)
    private double precioSinIva;
    @Column(name = "precio_venta_publico_manual", nullable = true)
    private double precioVentaPublicoManual;
    @Column(name = "utilidad", nullable = true)
    private double utilidad;
    @Column(name = "utilidad_porcentaje", nullable = true)
    private double utilidadPorcentaje;
    @Column(name = "estado", nullable = true)
    private String estado;
	@ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
	@ManyToOne
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    @ManyToOne
    @JoinColumn(name = "segmento_id", nullable = true)
    private Segmento segmento;

    public Precio(){
        super();
        this.costo = Constantes.cero;
        this.margenGanancia = Constantes.cero;
        this.precioVentaPublico = Constantes.cero;
        this.precioVentaPublicoIva = Constantes.cero;
        this.precioSinIva = Constantes.cero;
        this.precioVentaPublicoManual = Constantes.cero;
        this.utilidad = Constantes.cero;
        this.utilidadPorcentaje = Constantes.cero;
        this.estado = Constantes.activo;
        this.medida = new Medida();
        this.segmento = new Segmento();
    }

    public Precio(long id){
        super(id);
    }

    public Precio(List<String> datos){

    }

    public Precio(String codigo, double costo, double margen_ganancia,
                  double precioVentaPublico, double precioSinIva, double precioVentaPublicoManual,
                  double utilidad, double utilidadPocentaje, String estado, Producto producto, Medida medida, Segmento segmento){
        super(codigo);
        this.costo=costo;
        this.margenGanancia=margen_ganancia;
        this.precioVentaPublico=precioVentaPublico;
        this.precioSinIva=precioSinIva;
        this.precioVentaPublicoManual=precioVentaPublicoManual;
        this.utilidad=utilidad;
        this.utilidadPorcentaje=utilidadPocentaje;
        this.estado = estado;
        this.producto=producto;
        this.medida=medida;
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

    public double getPrecioSinIva() {
		return precioSinIva;
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
    
    public String getEstado() {
		return estado;
	}
    
    @JsonBackReference
    public Producto getProducto() {
		return producto;
	}
    public Medida getMedida() {
		return medida;
	}
    
    public Segmento getSegmento() {
        return segmento;
    }
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void normalizar(){
        if(this.medida == null) this.medida = new Medida();
        if(this.segmento == null) this.segmento = new Segmento();
    }

}
