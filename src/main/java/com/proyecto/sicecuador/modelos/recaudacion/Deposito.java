package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
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
    private CuentaPropia cuentaPropia;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;


    public Deposito(){
        super();
        this.fecha = new Date();
        this.comprobante = Constantes.vacio;
        this.valor = Constantes.cero;
        this.banco = new Banco();
        this.cuentaPropia = new CuentaPropia();
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

    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.cuentaPropia == null) this.cuentaPropia = new CuentaPropia();
    }
}
