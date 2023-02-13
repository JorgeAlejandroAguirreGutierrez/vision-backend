package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "precio")
@Data
@AllArgsConstructor
public class Precio extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
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
    @JoinColumn(name = "segmento_id", nullable = true)
    private Segmento segmento;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Precio(long id){
        super(id);
    }
    public Precio(){
        super();
        this.codigo = Constantes.vacio;
        this.costo = Constantes.cero;
        this.margenGanancia = Constantes.cero;
        this.precioVentaPublico = Constantes.cero;
        this.precioVentaPublicoIva = Constantes.cero;
        this.precioSinIva = Constantes.cero;
        this.precioVentaPublicoManual = Constantes.cero;
        this.utilidad = Constantes.cero;
        this.utilidadPorcentaje = Constantes.cero;
        this.estado = Constantes.activo;
        this.segmento = new Segmento();
    }

    public void normalizar(){
        if(this.segmento == null) this.segmento = new Segmento();
    }

}
