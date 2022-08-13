package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "impuesto")
public class Impuesto extends Entidad {
    @NotNull
    @Column(name="codigo_impuesto_sri",nullable = true)
    private String codigoImpuestoSri;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @NotNull
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
    @NotNull
    @Column(name="codigo_tarifa_sri",nullable = true)
    private String codigoTarifaSri;	
	@Column(name = "descripcion_tarifa", nullable = true)
    private String descripcionTarifa;	
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

    public Impuesto(String codigo, String codigoImpuestoSri, String abreviatura, String descripcion, String codigoTarifaSri, String descripcionTarifa, double porcentaje, String estado){
        super(codigo);
        this.codigoImpuestoSri=codigoImpuestoSri;
        this.abreviatura=abreviatura;
        this.descripcion=descripcion;
        this.codigoTarifaSri=codigoTarifaSri;
        this.descripcionTarifa=descripcionTarifa;
        this.porcentaje=porcentaje;
        this.estado=estado;
    }

    public Impuesto(List<String> datos){
        codigoImpuestoSri=datos.get(0)== null? null: datos.get(0);
    	abreviatura=datos.get(1)== null ? null: datos.get(1);
    	descripcion=datos.get(2)== null ? null: datos.get(2);
    	codigoTarifaSri=datos.get(3)== null? null: datos.get(3);
    	descripcionTarifa=datos.get(4)== null? null: datos.get(4);    	    	
        porcentaje=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        estado=datos.get(6)== null ? null: datos.get(6);
    }

    
    public String getCodigoImpuestoSri() {
		return codigoImpuestoSri;
	}

    
	public String getDescripcion() {
        return descripcion;
    }

    public String getCodigoTarifaSri() {
		return codigoTarifaSri;
	}

	public void setCodigoTarifaSri(String codigoTarifaSri) {
		this.codigoTarifaSri = codigoTarifaSri;
	}

	public String getDescripcionTarifa() {
		return descripcionTarifa;
	}

	public void setDescripcionTarifa(String descripcionTarifa) {
		this.descripcionTarifa = descripcionTarifa;
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
