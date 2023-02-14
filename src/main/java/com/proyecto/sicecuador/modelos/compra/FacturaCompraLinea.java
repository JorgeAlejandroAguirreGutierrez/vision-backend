package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "factura_compra_linea")
@Data
@AllArgsConstructor
public class FacturaCompraLinea extends Entidad {
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
    @Column(name = "total_sin_descuento", nullable = true)
    private double totalSinDescuentoLinea;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "factura_compra_id", nullable = true)
    private FacturaCompra facturaCompra;

    public FacturaCompraLinea(long id){
        super(id);
    }
    public FacturaCompraLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.comentario = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.porcentajeDescuentoLinea = Constantes.cero;
        this.totalSinDescuentoLinea = Constantes.cero;
        this.bodega = new Bodega();
        this.producto = new Producto();
    }

    public void normalizar(){
        if(this.bodega == null) this.bodega = new Bodega();
    }
}
