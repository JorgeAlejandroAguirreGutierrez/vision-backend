package com.proyecto.sicecuador.modelos.configuracion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.ParametroUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parametro")
@EntityListeners({ParametroUtil.class})
public class Parametro extends Entidad {
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("tabla")
    @Column(name = "tabla", nullable = true)
    private String tabla;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Parametro(){

    }

    public Parametro(long id){
        super(id);
    }

    public Parametro(String tipo){
        this.tipo=tipo;
    }

    public Parametro(String codigo, String tipo, String nombre, String tabla, String abreviatura){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.tabla=tabla;
        this.abreviatura=abreviatura;
    }
    public Parametro(String tabla,String tipo){
        this.tipo=tipo;
        this.tabla=tabla;
    }
    public Parametro(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        nombre=datos.get(1)== null ? null: datos.get(1);
        tabla=datos.get(2)== null ? null: datos.get(2);
        abreviatura=datos.get(3)== null ? null: datos.get(3);
    }
    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
