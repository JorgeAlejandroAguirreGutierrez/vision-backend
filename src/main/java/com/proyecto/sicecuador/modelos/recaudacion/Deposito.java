package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.DepositoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deposito")
@EntityListeners({DepositoUtil.class})
public class Deposito extends Entidad {
    @Column(name = "tipo_transaccion", nullable = true)
    private String tipo_transaccion;
    @Column(name = "numero_transaccion", nullable = true)
    private String numero_transaccion;
    @Column(name = "fecha_transaccion", nullable = true)
    private Date fecha_transaccion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public Deposito(){
    }

    public Deposito(long id){
        super(id);
    }

    public Deposito(String codigo, String tipo_transaccion, String numero_transaccion, Date fecha_transaccion, double valor, Recaudacion recaudacion, Banco banco){
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

    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public Banco getBanco() {
        return banco;
    }
}
