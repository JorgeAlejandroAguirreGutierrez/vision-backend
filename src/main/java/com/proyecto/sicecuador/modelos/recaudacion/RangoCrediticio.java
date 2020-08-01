package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rango_crediticio")
public class RangoCrediticio extends Entidad {
    @Column(name = "rango_inicial", nullable = true)
    private double rango_inicial;
    @Column(name = "rango_final", nullable = true)
    private double rango_final;
    @Column(name = "tasa_interes_anual", nullable = true)
    private double tasa_interes_anual;
    @Column(name = "tasa_periodo", nullable = true)
    private double tasa_periodo;

    public RangoCrediticio(){
    }

    public RangoCrediticio(long id){
        super(id);
    }

    public RangoCrediticio(String codigo, double rango_inicial, double rango_final, double tasa_interes_anual, double tasa_periodo){
        super(codigo);
        this.rango_inicial=rango_inicial;
        this.rango_final=rango_final;
        this.tasa_interes_anual=tasa_interes_anual;
        this.tasa_periodo=tasa_periodo;
    }

    public double getRango_inicial() {
        return rango_inicial;
    }

    public double getRango_final() {
        return rango_final;
    }

    public double getTasa_interes_anual() {
        return tasa_interes_anual;
    }

    public double getTasa_periodo() {
        return tasa_periodo;
    }
}
