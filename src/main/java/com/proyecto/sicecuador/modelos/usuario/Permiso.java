package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permiso")
public class Permiso extends Entidad {
	@JsonProperty("modulo")
    @Column(name = "modulo", nullable = true)
    private String modulo;
	@JsonProperty("operacion")
    @Column(name = "operacion", nullable = true)
    private String operacion;
	@JsonProperty("habilitado")
    @Column(name = "habilitado", nullable = true)
    private boolean habilitado;
    @ManyToOne
    @JsonProperty("perfil")
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;

    public Permiso(){
        super();
    }
    public Permiso(long id){
        super(id);
    }
    public Permiso(String codigo, String modulo, String operacion, boolean habilitado, Perfil perfil) {
        super(codigo);
        this.modulo = modulo;
        this.operacion = operacion;
        this.habilitado = habilitado;
        this.perfil = perfil;
    }

    public Permiso(List<String> datos){
        modulo=datos.get(0)== null ? null: datos.get(0);
        operacion=datos.get(1)== null ? null: datos.get(1);
        habilitado=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        perfil=datos.get(3)== null ? null:new Perfil((long) Double.parseDouble(datos.get(3)));
    }

    public String getModulo() {
        return modulo;
    }

    public String getOperacion() {
        return operacion;
    }

    public boolean isHabilitado() {
        return habilitado;
    }
    @JsonBackReference
    public Perfil getPerfil() {
        return perfil;
    }
}
