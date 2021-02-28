package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.usuario.PuntoVentaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "punto_venta")
public class PuntoVenta extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @ManyToOne
    @JsonProperty("establecimiento")
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public PuntoVenta(){
        super();
    }

    public PuntoVenta(long id) {
        super(id);
    }
    public PuntoVenta(String codigo, String descripcion, Establecimiento establecimiento){
        super(codigo);
        this.descripcion=descripcion;
        this.establecimiento=establecimiento;
    }
    public PuntoVenta(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        establecimiento=datos.get(1)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(1)));
    }
    public String getDescripcion() {
        return descripcion;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }
}
