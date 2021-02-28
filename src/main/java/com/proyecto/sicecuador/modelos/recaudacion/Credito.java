package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credito")
public class Credito extends Entidad {
	@JsonProperty("saldo")
    @Column(name = "saldo", nullable = true)
    private double saldo;
	@JsonProperty("tasa_interes_anual")
    @Column(name = "tasa_interes_anual", nullable = true)
    private double tasaInteresAnual;
	@JsonProperty("periodicidad")
    @Column(name = "periodicidad", nullable = true)
    private String periodicidad;
	@JsonProperty("periodicidad_numero")
    @Column(name = "periodicidad_numero", nullable = true)
    private int periodicidadNumero;
	@JsonProperty("periodicidad_total")
    @Column(name = "periodicidad_total", nullable = true)
    private int periodicidadTotal;
	@JsonProperty("tasa_periodo")
    @Column(name = "tasa_periodo", nullable = true)
    private double tasaPeriodo;
	@JsonProperty("cuotas")
    @Column(name = "cuotas", nullable = true)
    private long cuotas;
	@JsonProperty("fecha_primera_cuota")
    @Column(name = "fecha_primera_cuota", nullable = true)
    private Date fechaPrimeraCuota;
	@JsonProperty("fecha_consecion")
    @Column(name = "fecha_consecion", nullable = true)
    private Date fechaConsecion;
	@JsonProperty("dividendo")
    @Column(name = "dividendo", nullable = true)
    private double dividendo;
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("sin_intereses")
    @Column(name = "sin_intereses", nullable = true)
    private boolean sinIntereses;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("amortizaciones")
    @JoinColumn(name = "credito_id", nullable = true)
    private List<Amortizacion> amortizaciones;

    public Credito(){
    }

    public Credito(long id){
        super(id);
    }

    public Credito(String codigo, double saldo, double tasaInteresAnual, int periodicidadNumero, String periodicidad,
                   int periodicidadTotal, double tasaPeriodo, long cuotas,Date fechaPrimeraCuota, Date fechaConsecion,
                   double dividendo, String tipo, boolean sinIntereses, List<Amortizacion> amortizaciones){
        super(codigo);
        this.saldo=saldo;
        this.tasaInteresAnual=tasaInteresAnual;
        this.periodicidad=periodicidad;
        this.periodicidadNumero=periodicidadNumero;
        this.periodicidadTotal=periodicidadTotal;
        this.tasaPeriodo=tasaPeriodo;
        this.cuotas=cuotas;
        this.fechaPrimeraCuota=fechaPrimeraCuota;
        this.fechaConsecion=fechaConsecion;
        this.dividendo=dividendo;
        this.tipo=tipo;
        this.sinIntereses=sinIntereses;
        this.amortizaciones=amortizaciones;
    }
    public Credito(List<String>datos){
        saldo=datos.get(0)== null ? null: Double.parseDouble(datos.get(0));
        tasaInteresAnual=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
        periodicidad=datos.get(2)== null ? null: datos.get(2);
        periodicidadNumero=datos.get(3)== null ? null: (int)Double.parseDouble(datos.get(3));
        periodicidadTotal=datos.get(4)== null ? null: (int)Double.parseDouble(datos.get(4));
        tasaPeriodo=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        cuotas=datos.get(6)== null ? null: (long)Double.parseDouble(datos.get(6));
        fechaPrimeraCuota=datos.get(7)== null ? null: new Date(datos.get(7));
        fechaConsecion=datos.get(8)== null ? null: new Date(datos.get(8));
        dividendo=datos.get(9)== null ? null: (long)Double.parseDouble(datos.get(9));
        tipo=datos.get(10)== null ? null: datos.get(10);
        sinIntereses=datos.get(11)== null ? null: datos.get(11).equals("S") ? true : false;
    }
    public double getSaldo() {
        return saldo;
    }

    public double getTasaInteresAnual() {
		return tasaInteresAnual;
	}

    /**
     * Es el texto del tipo de peridicidad por ejemplo: mensual, trimestral
     * @return
     */
    public String getPeriodicidad() {
        return periodicidad;
    }

    /**
     * Es el numero de dias para el periodo por ejemplo: mensual=30, trimestral=90
     * @return
     */
    public int getPeriodicidadNumero() {
		return periodicidadNumero;
	}

    public int getPeriodicidadTotal() {
		return periodicidadTotal;
	}

    public double getTasaPeriodo() {
		return tasaPeriodo;
	}

    public void setTasaPeriodo(double tasaPeriodo) {
		this.tasaPeriodo = tasaPeriodo;
	}

    public long getCuotas() {
        return cuotas;
    }

    public Date getFechaConsecion() {
		return fechaConsecion;
	}

    public Date getFechaPrimeraCuota() {
		return fechaPrimeraCuota;
	}

    public double getDividendo() {
        return dividendo;
    }

    public String getTipo() {
        return tipo;
    }
    
    public boolean isSinIntereses() {
		return sinIntereses;
	}

    @JsonManagedReference
    public List<Amortizacion> getAmortizaciones() {
        return amortizaciones;
    }

    public void setAmortizaciones(List<Amortizacion> amortizaciones) {
        this.amortizaciones = amortizaciones;
    }

    public void setFechaConsecion(Date fechaConsecion) {
		this.fechaConsecion = fechaConsecion;
	}

    public void setTasaInteresAnual(double tasaInteresAnual) {
		this.tasaInteresAnual = tasaInteresAnual;
	}

    public void setPeriodicidadNumero(int periodicidadNumero) {
		this.periodicidadNumero = periodicidadNumero;
	}

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
