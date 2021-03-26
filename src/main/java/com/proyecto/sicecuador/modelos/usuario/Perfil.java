package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "perfil")
public class Perfil extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("permisos")
    @JoinColumn(name = "perfil_id")
    private List<Permiso> permisos;

    public Perfil(){
    }

    public Perfil(long id) {
        super(id);
    }

    public Perfil (String codigo, String descripcion, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public Perfil(List<String>datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        abreviatura=datos.get(1)== null ? null: datos.get(1);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    @JsonManagedReference
    public List<Permiso> getPermisos() {
        return permisos;
    }
}
