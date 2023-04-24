package com.proyecto.sicecuador.modelos.venta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.configuracion.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta_linea;

@Entity
@Table(name = tabla_nota_debito_venta_linea)
@Data
@AllArgsConstructor
public class NotaDebitoVentaLinea extends Entidad {
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
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "iva_sin_descuento_linea", nullable = true)
    private double ivaSinDescuentoLinea;
    @Column(name = "total_sin_descuento_linea", nullable = true)
    private double totalSinDescuentoLinea;
    @Column(name = "total_con_descuento_linea", nullable = true)
    private double totalConDescuentoLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private NotaDebitoVenta notaDebitoVentaLinea;

    public NotaDebitoVentaLinea(long id){
        super(id);
    }
    public NotaDebitoVentaLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.entregado = Constantes.no;
        this.consignacion = Constantes.no;
        this.cantidad = Constantes.ceroId;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.totalSinDescuentoLinea = Constantes.cero;
        this.totalConDescuentoLinea = Constantes.cero;
    }
}
