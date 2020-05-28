package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.entrega.TransportistaUtil;

import javax.persistence.*;

@Entity
@Table(name = "transportista")
@EntityListeners({TransportistaUtil.class})
public class Transportista extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "vehiculo_propio", nullable = true)
    private String vehiculo_propio;

    public Transportista(){

    }

    public Transportista(long id){
        super(id);
    }

    public Transportista(String codigo, String nombre, String identificacion, String vehiculo_propio){
        super(codigo);
        this.nombre=nombre;
        this.identificacion=identificacion;
        this.vehiculo_propio=vehiculo_propio;
    }
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getVehiculo_propio() {
        return vehiculo_propio;
    }
}
