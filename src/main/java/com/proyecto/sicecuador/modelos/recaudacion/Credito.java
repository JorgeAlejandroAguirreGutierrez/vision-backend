package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.CreditoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "credito")
@EntityListeners({CreditoUtil.class})
public class Credito extends Entidad {
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "plazo", nullable = true)
    private double plazo;
    @Column(name = "periodicidad", nullable = true)
    private String periodicidad;
    @Column(name = "modelo_tabla", nullable = true)
    private String modelo_tabla;
    @Column(name = "interes_periodo", nullable = true)
    private double interes_periodo;
    @Column(name = "interes_anual", nullable = true)
    private double interes_anual;
    @Column(name = "primera_cuota", nullable = true)
    private Date primera_cuota;
    @Column(name = "vencimiento", nullable = true)
    private Date vencimiento;
    @Column(name = "cuota", nullable = true)
    private double cuota;
    @Column(name = "intereses", nullable = true)
    private double intereses;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Credito(){
    }

    public Credito(long id){
        super(id);
    }

    public Credito(String codigo, double saldo, double plazo, String periodicidad, String modelo_tabla, double interes_periodo,
                   double interes_anual, Date primera_cuota, Date vencimiento,double cuota, double intereses, double total, String estado){
        super(codigo);
        this.saldo=saldo;
        this.plazo=plazo;
        this.periodicidad=periodicidad;
        this.modelo_tabla=modelo_tabla;
        this.interes_periodo=interes_periodo;
        this.interes_anual=interes_anual;
        this.primera_cuota=primera_cuota;
        this.vencimiento=vencimiento;
        this.cuota=cuota;
        this.intereses=intereses;
        this.total=total;
        this.estado=estado;
    }
    public double getSaldo() {
        return saldo;
    }

    public double getPlazo() {
        return plazo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public String getModelo_tabla() {
        return modelo_tabla;
    }

    public double getInteres_periodo() {
        return interes_periodo;
    }

    public double getInteres_anual() {
        return interes_anual;
    }

    public Date getPrimera_cuota() {
        return primera_cuota;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public double getCuota() {
        return cuota;
    }

    public double getIntereses() {
        return intereses;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }
}
