package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "modelo_tabla")
public class ModeloTabla extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "valor", nullable = true)
    private double valor;

    public ModeloTabla(){
        super();
    }

    public ModeloTabla(long id) {
        super(id);
    }

    public ModeloTabla(String codigo, String descripcion, double valor){
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
