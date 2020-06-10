package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "amortizacion")
public class Amortizacion extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "valor", nullable = true)
    private double valor;

    public Amortizacion(){
    }

    public Amortizacion(long id) {
        super(id);
    }

    public Amortizacion(String codigo, String descripcion, double valor){
        super(codigo);
        this.descripcion=descripcion;
        this.valor=valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }
}
