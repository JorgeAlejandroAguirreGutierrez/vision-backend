package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.PlazoCreditoUtil;

import javax.persistence.*;

@Entity
@Table(name = "plazo_credito")
@EntityListeners({PlazoCreditoUtil.class})
public class PlazoCredito extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "plazo", nullable = true)
    private double plazo;

    public PlazoCredito(){
        super();
    }

    public PlazoCredito(long id) {
        super(id);
    }

    public PlazoCredito(String codigo, String descripcion, double plazo){
        super(codigo);
        this.descripcion=descripcion;
        this.plazo=plazo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPlazo() {
        return plazo;
    }
}
