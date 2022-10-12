package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_gasto")
public class TipoGasto extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
    

    public TipoGasto(){

    }

    public TipoGasto(long id){
        super(id);
    }

    public TipoGasto(String codigo, String descripcion, String abreviatura, String estado){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }

    public TipoGasto(List<String> datos){

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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    
    
}
