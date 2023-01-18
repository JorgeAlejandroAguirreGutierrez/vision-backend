package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "impuesto")
public class Impuesto extends Entidad {
    @Column(name="codigo_sri",nullable = true)
    private String codigoSRI;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@Column(name = "descripcion", nullable = true)
    private String descripcion;	
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;
    @Column(name = "estado", nullable = true)
    private String estado;

    
    public Impuesto(){
        super();
    }

    public Impuesto(long id){
        super(id);
    }
    public Impuesto(double porcentaje){
        super();
        this.porcentaje=porcentaje;
    }

    public Impuesto(String codigo, String codigoSri, String abreviatura, String descripcion, double porcentaje, String estado){
        super(codigo);
        this.codigoSRI=codigoSri;
        this.abreviatura=abreviatura;
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
        this.estado=estado;
    }

    public Impuesto(List<String> datos){
        codigoSRI=datos.get(0)== null? null: datos.get(0);
    	abreviatura=datos.get(1)== null ? null: datos.get(1);
    	descripcion=datos.get(2)== null ? null: datos.get(2);	    	
        porcentaje=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        estado=datos.get(6)== null ? null: datos.get(6);
    }

    
    public String getCodigoSRI() {
		return codigoSRI;
	}
    
	public String getDescripcion() {
        return descripcion;
	}
    
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
