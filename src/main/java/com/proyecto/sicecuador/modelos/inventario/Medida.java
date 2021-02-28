package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.MedidaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medida")
@EntityListeners({MedidaUtil.class})
public class Medida extends Entidad {
	@JsonProperty("codigo_norma")
    @Column(name = "codigo_norma", nullable = true)
    private String codigoNorma;
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Medida(){
        super();
    }

    public Medida(long id){
        super(id);
    }

    public Medida(String codigo, String codigoNorma, String tipo, String descripcion, String abreviatura){
        super(codigo);
        this.codigoNorma=codigoNorma;
        this.tipo=tipo;
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }
    public Medida(List<String> datos){
        codigoNorma=datos.get(0)== null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
    }
    
    public String getCodigoNorma() {
		return codigoNorma;
	}

    public String getTipo() {
        return tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
