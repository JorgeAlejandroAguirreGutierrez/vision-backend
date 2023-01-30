package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estacion")
public class Estacion extends Entidad {
	@Column(name ="codigo_sri", nullable = true)
	private String codigoSRI;
	@Column(name = "nombre", nullable = true)
    private String nombre;
	@Column(name = "dispositivo", nullable = true)
    private String dispositivo;
	@Column(name = "estado", nullable = true)
    private String estado;
	@ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public Estacion(){
        super();
        this.codigoSRI = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.dispositivo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.establecimiento = new Establecimiento();
    }

    public Estacion(long id) {
        super(id);
    }
    public Estacion(String codigo, String codigoSRI, String nombre, String dispositivo, String estado, Establecimiento establecimiento){
        super(codigo);
        this.codigoSRI = codigoSRI;
        this.nombre = nombre;
        this.dispositivo = dispositivo;
        this.establecimiento=establecimiento;
        this.estado = estado;
    }
    public Estacion(List<String> datos){
    	codigoSRI=datos.get(0)==null ? null: datos.get(0);
        nombre=datos.get(1)== null ? null: datos.get(1);
        dispositivo=datos.get(2)== null ? null: datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3);
    }

    public String getCodigoSRI() {
		return codigoSRI;
	}
    
    public String getNombre() {
		return nombre;
	}
    
    public String getDispositivo() {
		return dispositivo;
	}
    
    public String getEstado() {
		return estado;
	}
    
    public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void normalizar(){
        if(this.establecimiento == null) this.establecimiento = new Establecimiento();
    }
}
