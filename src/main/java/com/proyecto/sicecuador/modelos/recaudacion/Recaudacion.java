package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.otros.recaudacion.RecaudacionUtil;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "total_depositos_transferencias", nullable = true)
    private double total_depositos_transferencias;
    @Column(name = "total_retenciones_compras", nullable = true)
    private double total_retenciones_compras;
    @ManyToOne
    @JoinColumn(name = "tarjeta_credito_id", nullable = true)
    private TarjetaCredito tarjeta_credito;
    @ManyToOne
    @JoinColumn(name = "tarjeta_debito_id", nullable = true)
    private TarjetaDebito tarjeta_debito;
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

    public Recaudacion(String codigo, Date fecha, double total, String comentario, double efectivo, double total_cheques, double total_depositos_transferencias, double total_retenciones_compras,
                       TarjetaCredito tarjeta_credito,TarjetaDebito tarjeta_debito, Credito credito, TipoComprobante tipo_comprobante, Factura comprobante, Sesion sesion){
        super(codigo);
        this.fecha=fecha;
        this.total=total;
        this.comentario=comentario;
        this.efectivo=efectivo;
        this.total_cheques=total_cheques;
        this.total_depositos_transferencias=total_depositos_transferencias;
        this.total_retenciones_compras=total_retenciones_compras;
        this.tarjeta_credito=tarjeta_credito;
        this.tarjeta_debito=tarjeta_debito;
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

    public double getTotal_depositos_transferencias() {
        return total_depositos_transferencias;
    }

    public double getTotal_retenciones_compras() {
        return total_retenciones_compras;
    }

    public TarjetaCredito getTarjeta_credito() {
        return tarjeta_credito;
    }

    public TarjetaDebito getTarjeta_debito() {
        return tarjeta_debito;
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
}
