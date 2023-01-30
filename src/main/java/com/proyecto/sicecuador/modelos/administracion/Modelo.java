package com.proyecto.sicecuador.modelos.administracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tecnico", nullable = true)
    private String nombreTecnico;
    @Column(name = "endpoint", nullable = true)
    private String endpoint;
    @Column(name = "estado", nullable = true)
    private String estado;
    public Modelo(){
        super();
        this.nombre = Constantes.vacio;
        this.nombreTecnico = Constantes.vacio;
        this.endpoint = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public Modelo(long id) {
        super(id);
    }

    public Modelo(String nombre, String nombreTecnico, String endpoint, String estado) {
        this.nombre = nombre;
        this.endpoint= endpoint;
        this.nombreTecnico=nombreTecnico;
        this.estado=estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEndpoint() {
        return endpoint;
    }
    
    public String getEstado() {
		return estado;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
