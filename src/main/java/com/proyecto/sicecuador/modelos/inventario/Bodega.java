package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bodega")
public class Bodega extends Entidad {
	@JsonProperty("codigo_interno")
    @Column(name = "codigo_interno", nullable = true)
    private String codigoInterno;
    public Bodega(){
    }
    public Bodega(long id){
        super(id);
    }
    public Bodega(String codigo){
        super(codigo);
    }
    public Bodega(String codigo, String codigoInterno){
        super(codigo);
        this.codigoInterno=codigoInterno;
    }

    public Bodega(List<String>datos){
        codigoInterno=datos.get(0)== null ? null: datos.get(0);
    }

    public String getCodigoInterno() {
		return codigoInterno;
	}
}
