package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
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
    @Column(name = "propio", nullable = true)
    private boolean vehiculo_propio;
    @OneToOne
    @JoinColumn(name = "vehiculo_transporte_id")
    private VehiculoTransporte vehiculo_transporte;

    public Transportista(){

    }

    public Transportista(long id){
        super(id);
    }

    public Transportista(String codigo, String nombre, String identificacion, boolean vehiculo_propio, VehiculoTransporte vehiculo_transporte){
        super(codigo);
        this.nombre=nombre;
        this.identificacion=identificacion;
        this.vehiculo_propio=vehiculo_propio;
        this.vehiculo_transporte=vehiculo_transporte;
    }

    public Transportista(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        vehiculo_propio=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        vehiculo_transporte=datos.get(3)== null ? null: new VehiculoTransporte((long) Double.parseDouble(datos.get(3)));
    }
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public boolean isVehiculo_propio() {
        return vehiculo_propio;
    }

    public VehiculoTransporte getVehiculo_transporte() {
        return vehiculo_transporte;
    }
}
