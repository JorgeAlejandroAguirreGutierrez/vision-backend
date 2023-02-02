package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "plazo_credito")
public class PlazoCredito extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "plazo", nullable = true)
    private double plazo;
    @Column(name = "estado", nullable = true)
    private String estado;
	
    public PlazoCredito(){
        super();
        this.descripcion = Constantes.vacio;
        this.plazo = Constantes.cero;
        this.estado = Constantes.activo;
    }

    public PlazoCredito(long id) {
        super(id);
    }

    public PlazoCredito(String codigo, String descripcion, String abreviatura, double plazo, String estado){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.plazo=plazo;
        this.estado=estado;
    }

    public PlazoCredito(List<String> datos) {
        descripcion=datos.get(0)== null? null : datos.get(0);
        abreviatura=datos.get(1)== null? null : datos.get(1);
        plazo=datos.get(2)== null? null : Double.parseDouble(datos.get(2));
        estado=datos.get(3)== null? null : datos.get(3);;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    public double getPlazo() {
        return plazo;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
