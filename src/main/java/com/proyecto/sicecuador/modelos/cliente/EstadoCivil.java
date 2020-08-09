package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;
import com.proyecto.sicecuador.otros.cliente.EstadoCivilUtil;

import javax.persistence.*;
import java.util.List;

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

    public EstadoCivil(List<String> datos){
        descripcion=datos.get(0)== null? null : datos.get(0);
        abreviatura=datos.get(1)== null? null : datos.get(1);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
