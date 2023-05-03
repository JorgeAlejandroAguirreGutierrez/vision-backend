package com.proyecto.sicecuador.modelos.venta;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta;

@Entity
@Table(name = tabla_nota_debito_venta)
@Getter
@Setter
@AllArgsConstructor
public class NotaDebitoVenta extends Entidad {
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
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @JsonManagedReference
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private List<NotaDebitoVentaLinea> notaDebitoVentaLineas;

    //RECAUDACION
    @Column(name = "total_recaudacion", nullable = true)
    private double totalRecaudacion;
    @Column(name = "por_pagar", nullable = true)
    private double porPagar;
    @Column(name = "efectivo", nullable = true)
    private double efectivo;
    @Column(name = "cambio", nullable = true)
    private double cambio;
    @Column(name = "total_cheques", nullable = true)
    private double totalCheques;
    @Column(name = "total_depositos", nullable = true)
    private double totalDepositos;
    @Column(name = "total_transferencias", nullable = true)
    private double totalTransferencias;
    @Column(name = "total_tarjetas_debitos", nullable = true)
    private double totalTarjetasDebitos;
    @Column(name = "total_tarjetas_creditos", nullable = true)
    private double totalTarjetasCreditos;
    @Column(name = "total_credito", nullable = true)
    private double totalCredito;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "cheque_id", nullable = true)
    private List<NotaDebitoVentaCheque> cheques;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "deposito_id", nullable = true)
    private List<NotaDebitoVentaDeposito> depositos;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "transferencia_id", nullable = true)
    private List<NotaDebitoVentaTransferencia> transferencias;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "tarjeta_debito_id", nullable = true)
    private List<NotaDebitoVentaTarjetaDebito> tarjetasDebitos;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "tarjeta_credito_id", nullable = true)
    private List<NotaDebitoVentaTarjetaCredito> tarjetasCreditos;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "credito_id", nullable = true)
    private NotaDebitoVentaCredito credito;

    public NotaDebitoVenta(long id){
        super(id);
    }
    public NotaDebitoVenta(){
        super();
        this.codigo = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.claveAcceso = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.fecha = new Date();
        this.estado = Constantes.estadoNoFacturada;
        this.subtotalSinDescuento = Constantes.cero;
        this.descuentoTotal = Constantes.cero;
        this.subtotalBase12SinDescuento = Constantes.cero;
        this.subtotalBase0SinDescuento = Constantes.cero;
        this.ivaSinDescuento = Constantes.cero;
        this.totalSinDescuento = Constantes.cero;
        this.totalConDescuento = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.notaDebitoVentaLineas = Collections.emptyList();

        //RECAUDACION
        this.porPagar = Constantes.cero;
        this.efectivo = Constantes.cero;
        this.cambio = Constantes.cero;
        this.totalCheques = Constantes.cero;
        this.totalDepositos = Constantes.cero;
        this.totalTransferencias = Constantes.cero;
        this.totalTarjetasDebitos = Constantes.cero;
        this.totalTarjetasCreditos = Constantes.cero;
        this.totalCredito = Constantes.cero;
        this.cheques = Collections.emptyList();
        this.depositos = Collections.emptyList();
        this.transferencias = Collections.emptyList();
        this.tarjetasDebitos = Collections.emptyList();
        this.tarjetasCreditos = Collections.emptyList();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.sesion == null) this.sesion = new Sesion();
        if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
        if(this.notaDebitoVentaLineas.isEmpty()) this.notaDebitoVentaLineas = Collections.emptyList();

        if(cheques.isEmpty()) this.cheques = Collections.emptyList();
        if(depositos.isEmpty()) this.depositos = Collections.emptyList();
        if(transferencias.isEmpty()) this.transferencias = Collections.emptyList();
        if(tarjetasDebitos.isEmpty()) this.tarjetasDebitos = Collections.emptyList();
        if(tarjetasCreditos.isEmpty()) this.tarjetasCreditos = Collections.emptyList();
    }
}
