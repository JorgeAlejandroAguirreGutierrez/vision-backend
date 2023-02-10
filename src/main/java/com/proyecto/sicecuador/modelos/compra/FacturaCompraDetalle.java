package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "factura_compra_detalle")
@Data
@AllArgsConstructor
public class FacturaCompraDetalle extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "total_con_descuento", nullable = true)
    private double totalSinDescuentoLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "factura_compra_id", nullable = true)
    private FacturaCompra facturaCompra;

    public FacturaCompraDetalle(long id){
        super(id);
    }
    public FacturaCompraDetalle() {
        super();
        this.codigo = Constantes.vacio;
        this.comentario = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.totalSinDescuentoLinea = Constantes.cero;
        this.impuesto = new Impuesto();
        this.producto = new Producto();
    }
}
