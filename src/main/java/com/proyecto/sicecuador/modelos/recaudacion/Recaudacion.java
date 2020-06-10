package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.otros.recaudacion.RecaudacionUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recaudacion")
@EntityListeners({RecaudacionUtil.class})
public class Recaudacion extends Entidad {
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "efectivo", nullable = true)
    private double efectivo;
    @Column(name = "total_cheques", nullable = true)
    private double total_cheques;
    @Column(name = "total_depositos", nullable = true)
    private double total_depositos;
    @Column(name = "total_transferencias", nullable = true)
    private double total_transferencias;
    @Column(name = "total_tarjetas_debitos", nullable = true)
    private double total_tarjetas_debitos;
    @Column(name = "total_tarjetas_creditos", nullable = true)
    private double total_tarjetas_creditos;
    @Column(name = "total_compensaciones", nullable = true)
    private double total_compensaciones;
    @Column(name = "total_retenciones_compras", nullable = true)
    private double total_retenciones_compras;
    @Column(name = "total_credito", nullable = true)
    private double total_credito;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cheque_id")
    private List<Cheque> cheques;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "deposito_id")
    private List<Deposito> depositos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "transferencia_id")
    private List<Transferencia> transferencias;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_credito_id")
    private List<TarjetaCredito> tarjetas_creditos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_debito_id")
    private List<TarjetaDebito> tarjetas_debitos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "compensacion_id")
    private List<Compensacion> compensaciones;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "retencion_compra_id")
    private List<Compensacion> retenciones_compras;
    @ManyToOne
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipo_comprobante;
    @ManyToOne
    @JoinColumn(name = "comprobante_id", nullable = true)
    private Factura comprobante;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;

    public Recaudacion(){
    }

    public Recaudacion(long id){
        super(id);
    }

    public Recaudacion(String codigo, Date fecha, double total, String comentario, double efectivo,
                       double total_cheques, double total_depositos, double total_transferencias,
                       double total_tarjetas_debitos, double total_tarjetas_creditos, double total_credito,
                       double total_compensaciones, double total_retenciones_compras, List<Cheque> cheques,
                       List<Deposito>depositos, List<Transferencia> transferencias,
                       List<TarjetaCredito> tarjetas_creditos, List<TarjetaDebito> tarjetas_debitos, Credito credito,
                       TipoComprobante tipo_comprobante, Factura comprobante, Sesion sesion){
        super(codigo);
        this.fecha=fecha;
        this.total=total;
        this.comentario=comentario;
        this.efectivo=efectivo;
        this.total_cheques=total_cheques;
        this.total_depositos=total_depositos;
        this.total_transferencias=total_transferencias;
        this.total_tarjetas_debitos=total_tarjetas_debitos;
        this.total_tarjetas_creditos=total_tarjetas_creditos;
        this.total_compensaciones=total_compensaciones;
        this.total_retenciones_compras=total_retenciones_compras;
        this.total_credito=total_credito;

        this.cheques=cheques;
        this.depositos=depositos;
        this.transferencias=transferencias;
        this.tarjetas_creditos=tarjetas_creditos;
        this.tarjetas_debitos=tarjetas_debitos;
        this.credito=credito;
        this.tipo_comprobante=tipo_comprobante;
        this.comprobante=comprobante;
        this.sesion=sesion;
    }
    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public String getComentario() {
        return comentario;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public double getTotal_cheques() {
        return total_cheques;
    }

    public double getTotal_depositos() {
        return total_depositos;
    }

    public double getTotal_transferencias() {
        return total_transferencias;
    }

    public double getTotal_tarjetas_creditos() {
        return total_tarjetas_creditos;
    }

    public double getTotal_tarjetas_debitos() {
        return total_tarjetas_debitos;
    }

    public double getTotal_compensaciones() {
        return total_compensaciones;
    }

    public double getTotal_credito() {
        return total_credito;
    }

    public double getTotal_retenciones_compras() {
        return total_retenciones_compras;
    }

    @JsonManagedReference
    public List<Cheque> getCheques() {
        return cheques;
    }
    @JsonManagedReference
    public List<Deposito> getDepositos() {
        return depositos;
    }
    @JsonManagedReference
    public List<Transferencia> getTransferencias() {
        return transferencias;
    }
    @JsonManagedReference
    public List<TarjetaDebito> getTarjetas_debitos() {
        return tarjetas_debitos;
    }
    @JsonManagedReference
    public List<TarjetaCredito> getTarjetas_creditos() {
        return tarjetas_creditos;
    }
    @JsonManagedReference
    public List<Compensacion> getCompensaciones() {
        return compensaciones;
    }
    @JsonManagedReference
    public List<Compensacion> getRetenciones_compras() {
        return retenciones_compras;
    }

    public Credito getCredito() {
        return credito;
    }

    public TipoComprobante getTipo_comprobante() {
        return tipo_comprobante;
    }

    public Factura getComprobante() {
        return comprobante;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void normalizar(){
        if (this.credito.getPlazo_credito().getId()==0 || this.credito.getAmortizacion().getId()==0 || this.credito.getModelo_tabla().getId()==0
        || this.credito.getInteres_periodo()== 0 || this.credito.getInteres_anual()==0 || this.credito.getValor_seguro()== 0 || this.credito.getRecargos() == 0
        || this.credito.getSaldo()== 0){
            this.credito=null;
        }
    }

}
