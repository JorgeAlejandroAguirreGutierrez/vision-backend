package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "banco")
public class Banco extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Banco(){
        super();
    }

    public Banco(long id){
        super(id);
    }

    public Banco(String codigo, String tipo, String nombre, String abreviatura){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.abreviatura=abreviatura;
    }
    public Banco(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        nombre=datos.get(1)== null ? null: datos.get(1);
        abreviatura=datos.get(2)== null ? null: datos.get(2);
    }
    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
