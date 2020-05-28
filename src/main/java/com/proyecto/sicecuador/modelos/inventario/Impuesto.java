package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.ImpuestoUtil;

import javax.persistence.*;

@Entity
@Table(name = "impuesto")
@EntityListeners({ImpuestoUtil.class})
public class Impuesto extends Entidad {
    @Column(name = "codigo_norma", nullable = true)
    private String codigo_norma;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;

    public Impuesto(){
        super();
    }

    public Impuesto(long id){
        super(id);
    }
    public Impuesto(double porcentaje){
        super();
        this.porcentaje=porcentaje;
    }

    public Impuesto(String codigo, String codigo_norma,double porcentaje){
        super(codigo);
        this.codigo_norma=codigo_norma;
        this.porcentaje=porcentaje;
    }
    public String getCodigo_norma() {
        return codigo_norma;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
