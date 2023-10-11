package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_factura_compra_linea;

@Entity
@Table(name = tabla_factura_compra_linea)
@Getter
@Setter
@AllArgsConstructor
public class FacturaCompraLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "nombre_producto", nullable = true)
    private String nombreProducto;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "costo_distribuido", nullable = true)
    private double costoDistribuido;
    @Column(name = "costo_promedio", nullable = true)
    private double costoPromedio;
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "valor_porcentaje_descuento_linea", nullable = true)
    private double valorPorcentajeDescuentoLinea;
    @Column(name = "valor_descuento_total_linea", nullable = true)
    private double valorDescuentoTotalLinea;
    @Column(name = "valor_porcentaje_descuento_total_linea", nullable = true)
    private double valorPorcentajeDescuentoTotalLinea;
    @Column(name = "subtotal_linea_sin_descuento", nullable = true)
    private double subtotalLineaSinDescuento;
    @Column(name = "subtotal_linea", nullable = true)
    private double subtotalLinea;
    @Column(name = "importe_iva_linea", nullable = true)
    private double importeIvaLinea;
    @Column(name = "total_linea", nullable = true)
    private double totalLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_compra_id", nullable = true)
    private FacturaCompra facturaCompra;

    public FacturaCompraLinea(long id){
        super(id);
    }
    public FacturaCompraLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.nombreProducto = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.costoDistribuido = Constantes.cero;
        this.costoPromedio = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.valorPorcentajeDescuentoLinea = Constantes.cero;
        this.valorDescuentoTotalLinea = Constantes.cero;
        this.valorPorcentajeDescuentoTotalLinea = Constantes.cero;
        this.subtotalLineaSinDescuento = Constantes.cero;
        this.subtotalLinea = Constantes.cero;
        this.importeIvaLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }

    public void normalizar(){
        if(this.producto == null) this.producto = new Producto();
        if(this.impuesto == null) this.impuesto = new Impuesto();
        if(this.bodega == null) this.bodega = new Bodega();
    }
}
