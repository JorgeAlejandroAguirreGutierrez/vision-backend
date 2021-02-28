package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.DepositoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deposito")
public class Deposito extends Entidad {
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("comprobante")
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
	@JsonProperty("valor")
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JsonProperty("banco")
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JsonProperty("cuenta_propia")
    @JoinColumn(name = "cuenta_propia_id", nullable = true)
    private CuentaPropia cuentaPropia;
    @ManyToOne
    @JsonProperty("recaudacion")
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;


    public Deposito(){
    }

    public Deposito(long id){
        super(id);
    }

    public Deposito(String codigo, Date fecha, String comprobante, double valor, Banco banco, CuentaPropia cuentaPropia, Recaudacion recaudacion){
        super(codigo);
        this.fecha=fecha;
        this.comprobante=comprobante;
        this.valor=valor;
        this.banco=banco;
        this.cuentaPropia=cuentaPropia;
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

    public CuentaPropia getCuentaPropia() {
		return cuentaPropia;
	}

    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
