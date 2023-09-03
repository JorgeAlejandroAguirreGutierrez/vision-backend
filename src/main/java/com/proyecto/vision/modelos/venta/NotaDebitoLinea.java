package com.proyecto.vision.modelos.venta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import static com.proyecto.vision.Constantes.tabla_nota_debito_linea;

@Entity
@Table(name = tabla_nota_debito_linea)
@Getter
@Setter
@AllArgsConstructor
public class NotaDebitoLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "entregado", nullable = true)
    private String entregado;
    @Column(name = "consignacion", nullable = true)
    private String consignacion;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "precio_unitario", nullable = true)
    private double precioUnitario;
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "valor_porcentaje_descuento_linea", nullable = true)
    private double valorPorcentajeDescuentoLinea;
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
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_debito_id", nullable = true)
    private NotaDebito notaDebito;

    public NotaDebitoLinea(long id){
        super(id);
    }
    public NotaDebitoLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.entregado = Constantes.no;
        this.consignacion = Constantes.no;
        this.cantidad = Constantes.ceroId;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.valorPorcentajeDescuentoLinea = Constantes.cero;
        this.subtotalLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }
}