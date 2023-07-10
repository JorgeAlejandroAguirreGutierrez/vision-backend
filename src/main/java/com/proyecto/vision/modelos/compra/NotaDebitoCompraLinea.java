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
import static com.proyecto.vision.Constantes.tabla_nota_debito_compra_linea;

@Entity
@Table(name = tabla_nota_debito_compra_linea)
@Getter
@Setter
@AllArgsConstructor
public class NotaDebitoCompraLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
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
    @Column(name = "entregado", nullable = true)
    private String entregado;
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
    @JoinColumn(name = "nota_debito_compra_id", nullable = true)
    private NotaDebitoCompra notaDebitoCompra;

    public NotaDebitoCompraLinea(long id){
        super(id);
    }
    public NotaDebitoCompraLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.totalSinDescuentoLinea = Constantes.cero;
        this.entregado = Constantes.no;
    }
}