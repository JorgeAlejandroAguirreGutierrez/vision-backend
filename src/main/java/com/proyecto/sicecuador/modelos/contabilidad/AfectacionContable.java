package com.proyecto.sicecuador.modelos.contabilidad;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "afectacion_contable")
public class AfectacionContable extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public AfectacionContable(){
        super();
    }

    public AfectacionContable(long id){
        super(id);
    }

    public AfectacionContable(String codigo, String descripcion, String abreviatura, String estado){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }
    
    public AfectacionContable(List<String> datos){
    	descripcion=datos.get(0)== null ? null: datos.get(0);
    	abreviatura=datos.get(1)== null ? null: datos.get(1);
    	estado=datos.get(2)== null ? null: datos.get(2);
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
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
   
}
