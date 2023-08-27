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

import static com.proyecto.vision.Constantes.tabla_nota_credito;

@Entity
@Table(name = tabla_nota_credito)
@Getter
@Setter
@AllArgsConstructor
public class NotaCredito extends Entidad {
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
    @Column(name = "estado_sri", nullable = true)
    private String estadoSRI;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "descuento", nullable = true)
    private double descuento;
    @Column(name = "subtotal", nullable = true)
    private double subtotal;
    @Column(name = "subtotal_gravado", nullable = true)
    private double subtotalGravado;
    @Column(name = "subtotal_no_gravado", nullable = true)
    private double subtotalNoGravado;
    @Column(name = "importe_iva", nullable = true)
    private double importeIva;
    @Column(name = "total", nullable = true)
    private double total;
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
    private List<NotaCreditoLinea> notaCreditoLineas;

    public NotaCredito(long id){
        super(id);
    }
    public NotaCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.establecimiento = Constantes.vacio;
        this.puntoVenta = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.numeroComprobante = Constantes.vacio;
        this.claveAcceso = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.fecha = new Date();
        this.fechaAutorizacion = null;
        this.estado = Constantes.vacio;
        this.estadoSRI = Constantes.vacio;
        this.descuento = Constantes.cero;
        this.subtotal = Constantes.cero;
        this.subtotalGravado = Constantes.cero;
        this.subtotalNoGravado = Constantes.cero;
        this.importeIva = Constantes.cero;
        this.total = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.notaCreditoLineas = Collections.emptyList();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.sesion == null) this.sesion = new Sesion();
        if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
        if(this.notaCreditoLineas.isEmpty()) this.notaCreditoLineas = Collections.emptyList();
    }
}