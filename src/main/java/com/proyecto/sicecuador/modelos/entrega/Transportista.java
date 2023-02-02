package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transportista")
public class Transportista extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "vehiculo_propio", nullable = true)
    private String vehiculoPropio;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "vehiculo_transporte_id", nullable = true)
    private VehiculoTransporte vehiculoTransporte;

    public Transportista(){
        super();
        this.nombre = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.vehiculoPropio = Constantes.si;
        this.estado = Constantes.activo;
        this.vehiculoTransporte = new VehiculoTransporte();
    }

    public Transportista(long id){
        super(id);
    }

    public Transportista(String codigo, String nombre, String identificacion, String vehiculoPropio, String estado, VehiculoTransporte vehiculoTransporte){
        super(codigo);
        this.nombre=nombre;
        this.identificacion=identificacion;
        this.vehiculoPropio=vehiculoPropio;
        this.vehiculoTransporte=vehiculoTransporte;
        this.estado=estado;
    }

    public Transportista(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        vehiculoPropio=datos.get(2)== null ? null: datos.get(2);
        vehiculoTransporte=datos.get(3)== null ? null: new VehiculoTransporte((long) Double.parseDouble(datos.get(3)));
    }
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getVehiculoPropio() {
		return vehiculoPropio;
	}
    
    public void setVehiculoPropio(String vehiculoPropio) {
		this.vehiculoPropio = vehiculoPropio;
	}
    
    public VehiculoTransporte getVehiculoTransporte() {
		return vehiculoTransporte;
	}
    
    public String getEstado() {
		return estado;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void normalizar(){
        if(this.vehiculoTransporte == null) this.vehiculoTransporte = new VehiculoTransporte();
    }
}
