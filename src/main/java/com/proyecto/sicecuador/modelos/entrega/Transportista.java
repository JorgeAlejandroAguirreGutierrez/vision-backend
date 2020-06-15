package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.otros.entrega.TransportistaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transportista")
@EntityListeners({TransportistaUtil.class})
public class Transportista extends Entidad {

    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @OneToOne
    @JoinColumn(name = "vehiculo_transporte_id")
    private VehiculoTransporte vehiculo_transporte;

    public Transportista(){

    }

    public Transportista(long id){
        super(id);
    }

    public Transportista(String codigo, String nombre, String identificacion, VehiculoTransporte vehiculo_transporte){
        super(codigo);
        this.nombre=nombre;
        this.identificacion=identificacion;
        this.vehiculo_transporte=vehiculo_transporte;
    }
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public VehiculoTransporte getVehiculo_transporte() {
        return vehiculo_transporte;
    }
}
