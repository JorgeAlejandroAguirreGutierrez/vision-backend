package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.EfectivoUtil;

import javax.persistence.*;

@Entity
@Table(name = "efectivo")
@EntityListeners({EfectivoUtil.class})
public class Efectivo extends Entidad {
    @Column(name = "valor", nullable = true)
    private double valor;

    public Efectivo(){
    }

    public Efectivo(long id){
        super(id);
    }

    public Efectivo(String codigo, double valor) {
        super(codigo);
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}
