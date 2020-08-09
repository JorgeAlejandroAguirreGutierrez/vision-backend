package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.otros.recaudacion.CreditoUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credito")
public class Credito extends Entidad {
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "tasa_interes_anual", nullable = true)
    private double tasa_interes_anual;
    @Column(name = "periodicidad", nullable = true)
    private String periodicidad;
    @Column(name = "periodicidad_numero", nullable = true)
    private int periodicidad_numero;
    @Column(name = "periodicidad_total", nullable = true)
    private int periodicidad_total;
    @Column(name = "tasa_periodo", nullable = true)
    private double tasa_periodo;
    @Column(name = "cuotas", nullable = true)
    private long cuotas;
    @Column(name = "fecha_primera_cuota", nullable = true)
    private Date fecha_primera_cuota;
    @Column(name = "fecha_consecion", nullable = true)
    private Date fecha_consecion;
    @Column(name = "dividendo", nullable = true)
    private double dividendo;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "sin_intereses", nullable = true)
    private boolean sin_intereses;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "amortizacion_id", nullable = true)
    private List<Amortizacion> amortizaciones;

    public Credito(){
    }

    public Credito(long id){
        super(id);
    }

    public Credito(String codigo, double saldo, double tasa_interes_anual, int periodicidad_numero, String periodicidad,
                   int periodicidad_total, double tasa_periodo, long cuotas,Date fecha_primera_cuota, Date fecha_consecion,
                   double dividendo, String tipo, boolean sin_intereses, List<Amortizacion> amortizaciones){
        super(codigo);
        this.saldo=saldo;
        this.tasa_interes_anual=tasa_interes_anual;
        this.periodicidad=periodicidad;
        this.periodicidad_numero=periodicidad_numero;
        this.periodicidad_total=periodicidad_total;
        this.tasa_periodo=tasa_periodo;
        this.cuotas=cuotas;
        this.fecha_primera_cuota=fecha_primera_cuota;
        this.fecha_consecion=fecha_consecion;
        this.dividendo=dividendo;
        this.tipo=tipo;
        this.sin_intereses=sin_intereses;
        this.amortizaciones=amortizaciones;
    }
    public Credito(List<String>datos){
        saldo=datos.get(0)== null ? null: Double.parseDouble(datos.get(0));
        tasa_interes_anual=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
        periodicidad=datos.get(2)== null ? null: datos.get(2);
        periodicidad_numero=datos.get(3)== null ? null: (int)Double.parseDouble(datos.get(3));
        periodicidad_total=datos.get(4)== null ? null: (int)Double.parseDouble(datos.get(4));
        tasa_periodo=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        cuotas=datos.get(6)== null ? null: (long)Double.parseDouble(datos.get(6));
        fecha_primera_cuota=datos.get(7)== null ? null: new Date(datos.get(7));
        fecha_consecion=datos.get(8)== null ? null: new Date(datos.get(8));
        dividendo=datos.get(9)== null ? null: (long)Double.parseDouble(datos.get(9));
        tipo=datos.get(10)== null ? null: datos.get(10);
        sin_intereses=datos.get(11)== null ? null: datos.get(11).equals("S") ? true : false;
    }
    public double getSaldo() {
        return saldo;
    }

    public double getTasa_interes_anual() {
        return tasa_interes_anual;
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
    public int getPeriodicidad_numero() {
        return periodicidad_numero;
    }

    public int getPeriodicidad_total() {
        return periodicidad_total;
    }

    public double getTasa_periodo() {
        return tasa_periodo;
    }

    public void setTasa_periodo(double tasa_periodo) {
        this.tasa_periodo = tasa_periodo;
    }

    public long getCuotas() {
        return cuotas;
    }

    public Date getFecha_primera_cuota() {
        return fecha_primera_cuota;
    }

    public Date getFecha_consecion() {
        return fecha_consecion;
    }

    public double getDividendo() {
        return dividendo;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isSin_intereses() {
        return sin_intereses;
    }

    @JsonManagedReference
    public List<Amortizacion> getAmortizaciones() {
        return amortizaciones;
    }

    public void setAmortizaciones(List<Amortizacion> amortizaciones) {
        this.amortizaciones = amortizaciones;
    }

    public void setFecha_consecion(Date fecha_consecion) {
        this.fecha_consecion = fecha_consecion;
    }

    public void setTasa_interes_anual(double tasa_interes_anual) {
        this.tasa_interes_anual = tasa_interes_anual;
    }

    public void setPeriodicidad_numero(int periodicidad_numero) {
        this.periodicidad_numero = periodicidad_numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
