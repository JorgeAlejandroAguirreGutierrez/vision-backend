package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
@Entity
@Table(name = "direccion")
//@EntityListeners({DireccionUtil.class})
public class Direccion extends Entidad {
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @ManyToOne(optional = true)
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;

    public Direccion(){
        super();
    }

    public Direccion(long id) {
        super(id);
    }

    public Direccion(String codigo, String direccion, String referencia, String latitudgeo, String longitudgeo, Ubicacion ubicacion){
        super(codigo);
        this.direccion=direccion;
        this.referencia=referencia;
        this.latitudgeo=latitudgeo;
        this.longitudgeo=longitudgeo;
        this.ubicacion=ubicacion;
    }
    public String getDireccion() {
        return direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getLatitudgeo() {
        return latitudgeo;
    }

    public String getLongitudgeo() {
        return longitudgeo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
