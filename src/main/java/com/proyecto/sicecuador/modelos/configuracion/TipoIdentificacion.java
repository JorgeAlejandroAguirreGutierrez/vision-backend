package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_identificacion")
public class TipoIdentificacion extends Entidad {
	@Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    public TipoIdentificacion(){

    }

    public TipoIdentificacion(long id){
        super(id);
    }

    public TipoIdentificacion(String codigo, String codigoSRI, String descripcion) {
        super(codigo);
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
    }
    
    public TipoIdentificacion(List<String> datos){
        codigoSRI=datos.get(0)== null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);

    }
    
    public String getCodigoSRI() {
		return codigoSRI;
	}

    public String getDescripcion() {
	   return descripcion;
    }
}