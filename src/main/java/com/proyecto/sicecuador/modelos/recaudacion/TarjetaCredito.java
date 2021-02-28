package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito extends Entidad {
	@JsonProperty("diferido")
    @Column(name = "diferido", nullable = true)
    private boolean diferido;
	@JsonProperty("titular")
    @Column(name = "titular", nullable = true)
    private boolean titular;
	@JsonProperty("identificacion")
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
	@JsonProperty("nombre_titular")
    @Column(name = "nombre_titular", nullable = true)
    private String nombreTitular;
	@JsonProperty("lote")
    @Column(name = "lote", nullable = true)
    private String lote;
	@JsonProperty("valor")
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JsonProperty("operador_tarjeta")
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operadorTarjeta;
    @ManyToOne
    @JsonProperty("franquicia_tarjeta")
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquiciaTarjeta;
    @ManyToOne
    @JsonProperty("recaudacion")
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public TarjetaCredito(){
    }

    public TarjetaCredito(long id){
        super(id);
    }

    public TarjetaCredito(String codigo, boolean diferido, boolean titular, String identificacion, String nombreTitular, String lote, double valor, OperadorTarjeta operadorTarjeta, FranquiciaTarjeta franquiciaTarjeta, Recaudacion recaudacion){
        super(codigo);
        this.diferido=diferido;
        this.titular=titular;
        this.identificacion=identificacion;
        this.nombreTitular=nombreTitular;
        this.lote=lote;
        this.valor=valor;
        this.operadorTarjeta=operadorTarjeta;
        this.franquiciaTarjeta=franquiciaTarjeta;
        this.recaudacion=recaudacion;
    }
    public boolean isDiferido() {
        return diferido;
    }

    public boolean isTitular() {
        return titular;
    }

    public String getIdentificacion() {
        return identificacion;
    }

	public String getNombreTitular() {
		return nombreTitular;
	}

    public String getLote() {
        return lote;
    }

    public double getValor() {
        return valor;
    }

    public OperadorTarjeta getOperadorTarjeta() {
		return operadorTarjeta;
	}

    public FranquiciaTarjeta getFranquiciaTarjeta() {
		return franquiciaTarjeta;
	}
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
