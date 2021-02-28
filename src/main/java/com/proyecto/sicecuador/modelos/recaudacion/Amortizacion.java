package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "amortizacion")
public class Amortizacion extends Entidad {
	@JsonProperty("numero_cuota")
    @Column(name = "numero_cuota", nullable = true)
    private int numeroCuota;
	@JsonProperty("fecha_pago")
    @Column(name = "fecha_pago", nullable = true)
    private Date fechaPago;
	@JsonProperty("numero_dias")
    @Column(name = "numero_dias", nullable = true)
    private long numeroDias;
	@JsonProperty("capital_inicio_periodo")
    @Column(name = "capital_inicio_periodo", nullable = true)
    private double capitalInicioPeriodo;
	@JsonProperty("capital")
    @Column(name = "capital", nullable = true)
    private double capital;
	@JsonProperty("interesesPeriodo")
    @Column(name = "intereses_periodo", nullable = true)
    private double interesesPeriodo;
	@JsonProperty("cuota")
    @Column(name = "cuota", nullable = true)
    private double valorCuota;
	@JsonProperty("saldo_capital")
    @Column(name = "saldo_capital", nullable = true)
    private double saldoCapital;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonProperty("credito")
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;

    public Amortizacion(){
    }

    public Amortizacion(long id) {
        super(id);
    }

    public Amortizacion(String codigo, int numeroCuota, Date fechaPago, long numeroDias, double capitalInicioPeriodo,
                        double capital, double interesesPeriodo, double valorCuota, double saldoCapital, Credito credito){
        super(codigo);
        this.numeroCuota=numeroCuota;
        this.fechaPago=fechaPago;
        this.numeroDias=numeroDias;
        this.capitalInicioPeriodo=capitalInicioPeriodo;
        this.capital=capital;
        this.interesesPeriodo=interesesPeriodo;
        this.valorCuota=valorCuota;
        this.saldoCapital=saldoCapital;
        this.credito=credito;
    }

    public Amortizacion(List<String> datos) {
        numeroCuota=datos.get(0)== null ? null: (int)Double.parseDouble(datos.get(0));
        fechaPago=datos.get(1)== null ? null: new Date(datos.get(1));
        numeroDias=datos.get(2)== null ? null: (long)Double.parseDouble(datos.get(2));
        capitalInicioPeriodo=datos.get(3)== null ? null: Double.parseDouble(datos.get(3));
        capital=datos.get(4)== null ? null: Double.parseDouble(datos.get(4));
        interesesPeriodo=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        valorCuota=datos.get(6)== null ? null: Double.parseDouble(datos.get(6));
        saldoCapital=datos.get(7)== null ? null: Double.parseDouble(datos.get(7));
        credito=datos.get(8)== null ? null: new Credito((long) Double.parseDouble(datos.get(8)));
    }

    public int getNumeroCuota() {
		return numeroCuota;
	}

    public Date getFechaPago() {
		return fechaPago;
	}

    public long getNumeroDias() {
		return numeroDias;
	}

    public double getCapitalInicioPeriodo() {
		return capitalInicioPeriodo;
	}

    public double getCapital() {
        return capital;
    }

    public double getInteresesPeriodo() {
		return interesesPeriodo;
	}

    public double getValorCuota() {
		return valorCuota;
	}

    public double getSaldoCapital() {
		return saldoCapital;
	}
    
    @JsonBackReference
    public Credito getCredito() {
        return credito;
    }
}
