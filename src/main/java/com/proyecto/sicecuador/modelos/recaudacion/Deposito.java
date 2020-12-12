package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.DepositoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deposito")
public class Deposito extends Entidad {
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "cuenta_propia_id", nullable = true)
    private CuentaPropia cuenta_propia;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;


    public Deposito(){
    }

    public Deposito(long id){
        super(id);
    }

    public Deposito(String codigo, Date fecha, String comprobante, double valor, Banco banco, CuentaPropia cuenta_propia, Recaudacion recaudacion){
        super(codigo);
        this.fecha=fecha;
        this.comprobante=comprobante;
        this.valor=valor;
        this.banco=banco;
        this.cuenta_propia=cuenta_propia;
        this.recaudacion=recaudacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getComprobante() {
        return comprobante;
    }

    public double getValor() {
        return valor;
    }

    public Banco getBanco() {
        return banco;
    }

    public CuentaPropia getCuenta_propia() {
        return cuenta_propia;
    }

    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
