package com.proyecto.vision.modelos.venta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_nota_credito_linea;

@Entity
@Table(name = tabla_nota_credito_linea)
@Getter
@Setter
@AllArgsConstructor
public class NotaCreditoLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre_producto", nullable = true)
    private String nombreProducto;
    @Column(name = "cantidad_venta", nullable = true)
    private long cantidadVenta;
    @Column(name = "costo_unitario_venta", nullable = true)
    private double costoUnitarioVenta;
    @Column(name = "cantidad", nullable = true)
    private double cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "subtotal_linea", nullable = true)
    private double subtotalLinea;
    @Column(name = "importe_iva_linea", nullable = true)
    private double importeIvaLinea;
    @Column(name = "descuento_linea", nullable = true)
    private double descuentoLinea;
    @Column(name = "total_linea", nullable = true)
    private double totalLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_credito_id", nullable = true)
    private NotaCredito notaCredito;

    public NotaCreditoLinea(long id){
        super(id);
    }
    public NotaCreditoLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.nombreProducto = Constantes.vacio;
        this.cantidadVenta = Constantes.ceroId;
        this.costoUnitarioVenta = Constantes.cero;
        this.cantidad = Constantes.cero;
        this.costoUnitario = Constantes.cero;
        this.subtotalLinea = Constantes.cero;
        this.importeIvaLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }
}
