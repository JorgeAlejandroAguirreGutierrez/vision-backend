package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.ImpuestoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "impuesto")
@EntityListeners({ImpuestoUtil.class})
public class Impuesto extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("porcentaje")
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

    public Impuesto(String codigo, String descripcion,double porcentaje){
        super(codigo);
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
    }

    public Impuesto(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        porcentaje=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
