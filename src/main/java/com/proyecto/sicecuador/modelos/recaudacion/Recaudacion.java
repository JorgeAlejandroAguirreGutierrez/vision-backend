package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recaudacion")
@Data
@AllArgsConstructor
public class Recaudacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "comentario", nullable = true)
    private String comentario;
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
    @Column(name = "efectivo_codigo_sri", nullable = true)
    private String efectivoCodigoSri;
    @Column(name = "cheque_codigo_sri", nullable = true)
    private String chequeCodigoSri;
    @Column(name = "deposito_codigo_sri", nullable = true)
    private String depositoCodigoSri;
    @Column(name = "transferencia_codigo_sri", nullable = true)
    private String transferenciaCodigoSri;
    @Column(name = "tarjeta_debito_codigo_sri", nullable = true)
    private String tarjetaDebitoCodigoSri;
    @Column(name = "tarjeta_credito_codigo_sri", nullable = true)
    private String tarjetaCreditoCodigoSri;
    @Column(name = "credito_codigo_sri", nullable = true)
    private String creditoCodigoSri;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @JsonManagedReference
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cheque_id", nullable = true)
    private List<Cheque> cheques;
    @JsonManagedReference
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "deposito_id", nullable = true)
    private List<Deposito> depositos;
    @JsonManagedReference
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "transferencia_id", nullable = true)
    private List<Transferencia> transferencias;
    @JsonManagedReference
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_debito_id", nullable = true)
    private List<TarjetaDebito> tarjetasDebitos;
    @JsonManagedReference
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_credito_id", nullable = true)
    private List<TarjetaCredito> tarjetasCreditos;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;

    public Recaudacion(long id){
        super(id);
    }
    public Recaudacion(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.total = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.porPagar = Constantes.cero;
        this.efectivo = Constantes.cero;
        this.cambio = Constantes.cero;
        this.totalCheques = Constantes.cero;
        this.totalDepositos = Constantes.cero;
        this.totalTransferencias = Constantes.cero;
        this.totalTarjetasDebitos = Constantes.cero;
        this.totalTarjetasCreditos = Constantes.cero;
        this.totalCredito = Constantes.cero;
        this.efectivoCodigoSri = Constantes.vacio;
        this.chequeCodigoSri = Constantes.vacio;
        this.depositoCodigoSri = Constantes.vacio;
        this.transferenciaCodigoSri = Constantes.vacio;
        this.tarjetaDebitoCodigoSri = Constantes.vacio;
        this.tarjetaCreditoCodigoSri = Constantes.vacio;
        this.creditoCodigoSri = Constantes.vacio;
        this.estado = Constantes.activo;
        this.factura = new Factura();
        this.sesion = new Sesion();
        this.cheques = Collections.emptyList();
        this.depositos = Collections.emptyList();
        this.transferencias = Collections.emptyList();
        this.tarjetasDebitos = Collections.emptyList();
        this.tarjetasCreditos = Collections.emptyList();
        this.credito = new Credito();
    }
    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.factura == null) this.factura = new Factura();
        if(this.sesion == null) this.sesion = new Sesion();
        if(this.cheques == null) this.cheques = Collections.emptyList();
        if(this.depositos == null) this.depositos = Collections.emptyList();
        if(this.transferencias == null) this.transferencias = Collections.emptyList();
        if(this.tarjetasDebitos == null) this.tarjetasDebitos = Collections.emptyList();
        if(this.tarjetasCreditos == null) this.tarjetasCreditos = Collections.emptyList();
        if(this.credito == null) this.credito = new Credito();
    }
}
