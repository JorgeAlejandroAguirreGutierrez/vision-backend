package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "operador_tarjeta")
public class OperadorTarjeta extends Entidad {
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public OperadorTarjeta(){
    }

    public OperadorTarjeta(long id){
        super(id);
    }

    public OperadorTarjeta(String codigo, String tipo, String nombre, String abreviatura){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.abreviatura=abreviatura;
    }
    public OperadorTarjeta(String tipo){
        super();
        this.tipo=tipo;
    }
    public OperadorTarjeta(List<String> datos){
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
