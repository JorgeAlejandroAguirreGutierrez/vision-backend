package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "establecimiento")
public class Establecimiento extends Entidad {
	@NotNull
	@JsonProperty("direccion")
    @Column(name = "direccion", nullable = true)
    private String direccion;
	@NotNull
	@ManyToOne
    @JsonProperty("empresa")
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @NotNull
    @ManyToOne
    @JsonProperty("ubicacion")
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;

    public Establecimiento(){
        super();
    }
    public Establecimiento(long id){
        super(id);
    }

    public Establecimiento(String codigo, String direccion, Empresa empresa, Ubicacion ubicacion){
        super(codigo);
        this.direccion=direccion;
        this.empresa=empresa;
        this.ubicacion=ubicacion;
    }
    public Establecimiento(List<String> datos){
        direccion=datos.get(0)== null ? null: datos.get(0);
        empresa=datos.get(1)== null ? null:new Empresa((long) Double.parseDouble(datos.get(1)));
        ubicacion=datos.get(2)== null ? null:new Ubicacion((long) Double.parseDouble(datos.get(2)));
    }
    public String getDireccion() {
        return direccion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
}
