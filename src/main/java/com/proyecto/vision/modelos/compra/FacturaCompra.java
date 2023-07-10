package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.vision.Constantes.tabla_factura_compra;

@Entity
@Table(name = tabla_factura_compra)
@Getter
@Setter
@AllArgsConstructor
public class FacturaCompra extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "establecimiento", nullable = true)
    private String establecimiento;
    @Column(name = "punto_venta", nullable = true)
    private String puntoVenta;
    @Column(name = "secuencial", nullable = true)
    private String secuencial;
    @Column(name = "numero_comprobante", nullable = true)
    private String numeroComprobante;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "subtotal_sin_descuento", nullable = true)
    private double subtotalSinDescuento;
    @Column(name = "descuento_total", nullable = true)
    private double descuentoTotal;
    @Column(name = "subtotal_grabado_con_descuento", nullable = true)
    private double subtotalGrabadoConDescuento;
    @Column(name = "subtotal_no_grabado_con_descuento", nullable = true)
    private double subtotalNoGrabadoConDescuento;
    @Column(name = "importe_iva_total", nullable = true)
    private double importeIvaTotal;
    @Column(name = "valor_total", nullable = true)
    private double valorTotal;
    @Column(name = "valor_distribuido_total", nullable = true)
    private double valorDistribuidoTotal;
    @Column(name = "valor_descuento_total", nullable = true)
    private double valorDescuentoTotal;
    @Column(name = "porcentaje_descuento_total", nullable = true)
    private double porcentajeDescuentoTotal;
    @Column(name = "valor_porcentaje_descuento_total", nullable = true)
    private double valorPorcentajeDescuentoTotal;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @JsonManagedReference
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_compra_id", nullable = true)
    private List<FacturaCompraLinea> facturaCompraLineas;

    public FacturaCompra(long id){
        super(id);
    }
    public FacturaCompra(){
        super();
        this.codigo = Constantes.vacio;
        this.numeroComprobante = Constantes.vacio;
        this.fecha = new Date();
        this.estado = Constantes.activo;
        this.valorDistribuidoTotal = Constantes.cero;
        this.valorDescuentoTotal = Constantes.cero;
        this.porcentajeDescuentoTotal = Constantes.cero;
        this.valorPorcentajeDescuentoTotal = Constantes.cero;
        this.subtotalSinDescuento = Constantes.cero;
        this.descuentoTotal = Constantes.cero;
        this.subtotalGrabadoConDescuento = Constantes.cero;
        this.subtotalNoGrabadoConDescuento = Constantes.cero;
        this.importeIvaTotal = Constantes.cero;
        this.valorTotal = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.facturaCompraLineas = Collections.emptyList();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.proveedor == null) this.proveedor = new Proveedor();
        if(this.sesion == null) this.sesion = new Sesion();
        if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
        if(this.facturaCompraLineas.isEmpty()) this.facturaCompraLineas = Collections.emptyList();
    }
}