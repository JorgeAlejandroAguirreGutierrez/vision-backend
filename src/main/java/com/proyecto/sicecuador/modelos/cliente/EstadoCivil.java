package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;
import com.proyecto.sicecuador.otros.cliente.EstadoCivilUtil;

import javax.persistence.*;

@Entity
@Table(name = "estado_civil")
public class EstadoCivil extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public EstadoCivil(){

    }

    public EstadoCivil(long id) {
        super(id);
    }

    public EstadoCivil(String codigo, String descripcion, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
