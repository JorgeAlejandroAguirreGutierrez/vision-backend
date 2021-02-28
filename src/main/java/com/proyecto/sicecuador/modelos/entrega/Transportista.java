package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.entrega.TransportistaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transportista")
@EntityListeners({TransportistaUtil.class})
public class Transportista extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("identificacion")
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
	@JsonProperty("vehiculo_propio")
    @Column(name = "vehiculo_propio")
    private boolean vehiculoPropio;
    @OneToOne
    @JsonProperty("vehiculo_transporte")
    @JoinColumn(name = "vehiculo_transporte_id")
    private VehiculoTransporte vehiculoTransporte;

    public Transportista(){

    }

    public Transportista(long id){
        super(id);
    }

    public Transportista(String codigo, String nombre, String identificacion, boolean vehiculoPropio, VehiculoTransporte vehiculoTransporte){
        super(codigo);
        this.nombre=nombre;
        this.identificacion=identificacion;
        this.vehiculoPropio=vehiculoPropio;
        this.vehiculoTransporte=vehiculoTransporte;
    }

    public Transportista(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        vehiculoPropio=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        vehiculoTransporte=datos.get(3)== null ? null: new VehiculoTransporte((long) Double.parseDouble(datos.get(3)));
    }
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public boolean isVehiculoPropio() {
		return vehiculoPropio;
	}
    
    public void setVehiculoPropio(boolean vehiculoPropio) {
		this.vehiculoPropio = vehiculoPropio;
	}
    
    public VehiculoTransporte getVehiculoTransporte() {
		return vehiculoTransporte;
	}
}
