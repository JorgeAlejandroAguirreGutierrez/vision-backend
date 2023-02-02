package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cheque")
public class Cheque extends Entidad {
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
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Cheque(){
        super();
        this.numero = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.fecha = new Date();
        this.fechaEfectivizacion = new Date();
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }

    public Cheque(long id){
        super(id);
    }

    public Cheque(String codigo, String numero, String tipo, Date fecha, Date fechaEfectivizacion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.numero=numero;
        this.tipo=tipo;
        this.fecha=fecha;
        this.fechaEfectivizacion=fechaEfectivizacion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
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

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.fechaEfectivizacion == null) this.fechaEfectivizacion = new Date();
        if(this.banco == null) this.banco = new Banco();
    }

}
