package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cheque")
//@EntityListeners({ChequeUtil.class})
public class Cheque extends Entidad {
	@JsonProperty("numero")
    @Column(name = "numero", nullable = true)
    private String numero;
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("fecha_efectivizacion")
    @Column(name = "fecha_efectivizacion", nullable = true)
    private Date fechaEfectivizacion;
	@JsonProperty("valor")
	@Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JsonProperty("banco")
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JsonProperty("recaudacion")
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Cheque(){

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


}
