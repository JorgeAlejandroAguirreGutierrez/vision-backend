package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "segmento")
public class Segmento extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "margen_ganancia", nullable = true)
    private double margenGanancia;
	@Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@Column(name = "estado", nullable = true)
    private String estado;
	
    public Segmento(){
    	super();
        this.descripcion = Constantes.vacio;
        this.margenGanancia = Constantes.cero;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
    public Segmento(long id){
        super(id);
    }
    public Segmento(String codigo, String descripcion, double margenGanancia, String estado, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.margenGanancia=margenGanancia;
        this.estado=estado;
        this.abreviatura=abreviatura;
    }

    public Segmento(List<String>datos){
    	super(null);
    	descripcion=datos.get(0)==null ? null : datos.get(0);
    	margenGanancia=datos.get(1)==null ? null : Double.parseDouble(datos.get(1));
    	descripcion=datos.get(2)==null ? null : datos.get(2);
    	abreviatura=datos.get(3)==null ? null : datos.get(3);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMargenGanancia() {
        return margenGanancia;
    }

    public String getEstado() {
        return estado;
    }
    
    public String getAbreviatura() {
		return abreviatura;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
    public void setMargenGanancia(double margenGanancia) {
		this.margenGanancia = margenGanancia;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
    
}
