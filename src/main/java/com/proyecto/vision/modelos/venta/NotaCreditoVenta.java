package com.proyecto.vision.modelos.venta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.vision.Constantes.tabla_nota_credito_venta;

@Entity
@Table(name = tabla_nota_credito_venta)
@Getter
@Setter
@AllArgsConstructor
public class NotaCreditoVenta extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "serie", nullable = true)
    private String serie;
    @Column(name = "secuencial", nullable = true)
    private String secuencial;
    @Column(name = "codigo_numerico", nullable = true)
    private String codigoNumerico;
    @Column(name = "clave_acceso", nullable = true)
    private String claveAcceso;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_autorizacion", nullable = true)
    private Date fechaAutorizacion;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "subtotal_sin_descuento", nullable = true)
    private double subtotalSinDescuento;
    @Column(name = "descuento_total", nullable = true)
    private double descuentoTotal;
    @Column(name = "subtotal_base12_sin_descuento", nullable = true)
    private double subtotalBase12SinDescuento;
    @Column(name = "subtotal_base0_sin_descuento", nullable = true)
    private double subtotalBase0SinDescuento;
    @Column(name = "iva_sin_descuento", nullable = true)
    private double ivaSinDescuento;
    @Column(name = "total_sin_descuento", nullable = true)
    private double totalSinDescuento;
    @Column(name = "total_con_descuento", nullable = true)
    private double totalConDescuento;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
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
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
    @JoinColumn(name = "nota_credito_venta_id", nullable = true)
    private List<NotaCreditoVentaLinea> notaCreditoVentaLineas;

    public NotaCreditoVenta(long id){
        super(id);
    }
    public NotaCreditoVenta(){
        super();
        this.codigo = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.claveAcceso = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.fecha = new Date();
        this.fechaAutorizacion = null;
        this.estado = Constantes.estadoNoFacturada;
        this.subtotalSinDescuento = Constantes.cero;
        this.descuentoTotal = Constantes.cero;
        this.subtotalBase12SinDescuento = Constantes.cero;
        this.subtotalBase0SinDescuento = Constantes.cero;
        this.ivaSinDescuento = Constantes.cero;
        this.totalSinDescuento = Constantes.cero;
        this.totalConDescuento = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.notaCreditoVentaLineas = Collections.emptyList();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.sesion == null) this.sesion = new Sesion();
        if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
        if(this.notaCreditoVentaLineas.isEmpty()) this.notaCreditoVentaLineas = Collections.emptyList();
    }
}
