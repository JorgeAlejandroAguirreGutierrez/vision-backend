package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.configuracion.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_nota_credito_compra_linea;

@Entity
@Table(name = tabla_nota_credito_compra_linea)
@Getter
@Setter
@AllArgsConstructor
public class NotaCreditoCompraLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "devolucion", nullable = true)
    private long devolucion;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "iva_sin_descuento_linea", nullable = true)
    private double ivaSinDescuentoLinea;
    @Column(name = "total_sin_descuento", nullable = true)
    private double totalSinDescuentoLinea;
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
        this.cantidad = Constantes.ceroId;
        this.devolucion = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.ivaSinDescuentoLinea = Constantes.cero;
        this.totalSinDescuentoLinea = Constantes.cero;
    }
}
