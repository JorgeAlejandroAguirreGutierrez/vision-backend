package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.DepositoUtil;
import com.proyecto.sicecuador.otros.recaudacion.TransferenciaUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transferencia")
public class Transferencia extends Entidad {
    @Column(name = "tipo_transaccion", nullable = true)
    private String tipo_transaccion;
    @Column(name = "numero_transaccion", nullable = true)
    private String numero_transaccion;
    @Column(name = "fecha_transaccion", nullable = true)
    private Date fecha_transaccion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Transferencia(){
    }

    public Transferencia(long id){
        super(id);
    }

    public Transferencia(String codigo, String tipo_transaccion, String numero_transaccion, Date fecha_transaccion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.tipo_transaccion=tipo_transaccion;
        this.numero_transaccion=numero_transaccion;
        this.fecha_transaccion=fecha_transaccion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
    }
    public String getTipo_transaccion() {
        return tipo_transaccion;
    }

    public String getNumero_transaccion() {
        return numero_transaccion;
    }

    public Date getFecha_transaccion() {
        return fecha_transaccion;
    }

    public double getValor() {
        return valor;
    }

    public Banco getBanco() {
        return banco;
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
