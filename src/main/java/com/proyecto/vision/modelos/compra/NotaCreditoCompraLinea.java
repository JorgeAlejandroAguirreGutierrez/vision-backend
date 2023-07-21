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

import static com.proyecto.vision.Constantes.tabla_nota_credito_compra_linea;

@Entity
@Table(name = tabla_nota_credito_compra_linea)
@Getter
@Setter
@AllArgsConstructor
public class NotaCreditoCompraLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "cantidad_compra", nullable = true)
    private long cantidadCompra;
    @Column(name = "costo_unitario_compra", nullable = true)
    private double costoUnitarioCompra;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "subtotal_linea", nullable = true)
    private double subtotalLinea;
    @Column(name = "iva_linea", nullable = true)
    private double ivaLinea;
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
    @JoinColumn(name = "nota_credito_compra_id", nullable = true)
    private NotaCreditoCompra notaCreditoCompra;

    public NotaCreditoCompraLinea(long id){
        super(id);
    }
    public NotaCreditoCompraLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.cantidadCompra = Constantes.ceroId;
        this.costoUnitarioCompra = Constantes.cero;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.subtotalLinea = Constantes.cero;
        this.ivaLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }
}
