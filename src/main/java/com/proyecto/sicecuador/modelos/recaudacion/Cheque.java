package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.otros.recaudacion.ChequeUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cheque")
@EntityListeners({ChequeUtil.class})
public class Cheque extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "tipo_cheque", nullable = true)
    private String tipo_cheque;
    @Column(name = "fecha_cheque", nullable = true)
    private Date fecha_cheque;
    @Column(name = "fecha_efectivizacion", nullable = true)
    private Date fecha_efectivizacion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public Cheque(){

    }

    public Cheque(long id){
        super(id);
    }

    public Cheque(String codigo, String numero, String tipo_cheque, Date fecha_cheque, Date fecha_efectivizacion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.numero=numero;
        this.tipo_cheque=tipo_cheque;
        this.fecha_cheque=fecha_cheque;
        this.fecha_efectivizacion=fecha_efectivizacion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo_cheque() {
        return tipo_cheque;
    }

    public Date getFecha_cheque() {
        return fecha_cheque;
    }

    public Date getFecha_efectivizacion() {
        return fecha_efectivizacion;
    }

    public double getValor() {
        return valor;
    }

    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public Banco getBanco() {
        return banco;
    }
}
