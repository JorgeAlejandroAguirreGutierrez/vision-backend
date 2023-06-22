package com.proyecto.sicecuador.modelos.venta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_factura_linea;

@Entity
@Table(name = tabla_factura_linea)
@Getter
@Setter
@AllArgsConstructor
public class FacturaLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "entregado", nullable = true)
    private String entregado;
    @Column(name = "consignacion", nullable = true)
    private String consignacion;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "precio_unitario", nullable = true)
    private double precioUnitario;
    //LINEA
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "valor_porcentaje_descuento_linea", nullable = true)
    private double valorPorcentajeDescuentoLinea;
    @Column(name = "valor_descuento_total_linea", nullable = true)
    private double valorDescuentoTotalLinea;
    @Column(name = "porcentaje_descuento_total_linea", nullable = true)
    private double porcentajeDescuentoTotalLinea;
    @Column(name = "valor_porcentaje_descuento_total_linea", nullable = true)
    private double valorPorcentajeDescuentoTotalLinea;
    @Column(name = "subtotal_sin_descuento_linea", nullable = true)
    private double subtotalSinDescuentoLinea;
    @Column(name = "subtotal_con_descuento_linea", nullable = true)
    private double subtotalConDescuentoLinea;
    @Column(name = "importe_iva_linea", nullable = true)
    private double importeIvaLinea;
    @Column(name = "total_linea", nullable = true)
    private double totalLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @ManyToOne
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;

    public FacturaLinea(long id){
        super(id);
    }
    public FacturaLinea(){
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.comentario = Constantes.vacio;
        this.entregado = Constantes.no;
        this.consignacion = Constantes.no;
        this.cantidad = Constantes.ceroId;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.valorPorcentajeDescuentoLinea = Constantes.cero;
        this.valorDescuentoTotalLinea = Constantes.cero;
        this.porcentajeDescuentoTotalLinea = Constantes.cero;
        this.valorPorcentajeDescuentoTotalLinea = Constantes.cero;
        this.subtotalSinDescuentoLinea = Constantes.cero;
        this.subtotalConDescuentoLinea = Constantes.cero;
        this.importeIvaLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }

    public void normalizar(){
        if(this.impuesto == null) this.impuesto = new Impuesto();
        if(this.producto == null) this.producto = new Producto();
        if(this.bodega == null) this.bodega = new Bodega();
        if(this.precio == null) this.precio = new Precio();
    }
}

