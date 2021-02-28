package com.proyecto.sicecuador.modelos.compra;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class RetencionCompra extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_efectivizacion", nullable = true)
    private Date fechaEfectivizacion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public RetencionCompra(){

    }

    public RetencionCompra(long id){
        super(id);
    }

    public RetencionCompra(String codigo, String numero, String tipo, Date fecha, Date fechaEfectivizacion, double valor, Banco banco){
        super(codigo);
        this.numero=numero;
        this.tipo=tipo;
        this.fecha=fecha;
        this.fechaEfectivizacion=fechaEfectivizacion;
        this.valor=valor;
        this.banco=banco;
    }

    public double getValor() {
        return valor;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFechaEfectivizacion() {
		return fechaEfectivizacion;
	}

    public Banco getBanco() {
        return banco;
    }
}
