package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "direccion")
public class Direccion extends Entidad {
	@JsonProperty("direccion")
    @Column(name = "direccion", nullable = true)
    private String direccion;
	@JsonProperty("referencia")
    @Column(name = "referencia", nullable = true)
    private String referencia;
	@JsonProperty("latitudgeo")
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
	@JsonProperty("longitudgeo")
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @ManyToOne
    @JsonProperty("ubicacion")
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

    public Direccion(List<String> datos){
        direccion=datos.get(0)== null? null : datos.get(0);
        referencia=datos.get(1)== null? null : datos.get(1);
        latitudgeo=datos.get(2)== null? null : datos.get(2);
        longitudgeo=datos.get(3)== null? null : datos.get(3);
        ubicacion=datos.get(4)==null? null: new Ubicacion((long) Double.parseDouble(datos.get(4)));
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
