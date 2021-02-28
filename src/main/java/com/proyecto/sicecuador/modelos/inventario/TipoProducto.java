package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_producto")
//@EntityListeners({TipoProductoUtil.class})
public class TipoProducto extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public TipoProducto(){
        super();
    }

    public TipoProducto(long id){
        super(id);
    }

    public TipoProducto(List<String> datos){

    }
    public TipoProducto(String codigo, String descripcion, String tipo, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.tipo=tipo;
        this.abreviatura=abreviatura;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
