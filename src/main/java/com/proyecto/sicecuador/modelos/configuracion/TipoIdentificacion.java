package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.Constantes;
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
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
    public TipoIdentificacion(){
        super();
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public TipoIdentificacion(long id){
        super(id);
    }

    public TipoIdentificacion(String codigo, String codigoSRI, String descripcion, String abreviatura, String estado) {

        super(codigo);
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }
    
    public TipoIdentificacion(List<String> datos){
        codigoSRI=datos.get(0)== null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
        abreviatura=datos.get(2)== null? null : datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3);

    }
    
    public String getCodigoSRI() {
		return codigoSRI;
	}

    public String getDescripcion() {
	   return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public String getEstado() {
		return estado;
	}

}