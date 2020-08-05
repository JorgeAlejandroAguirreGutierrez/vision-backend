package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;
import com.proyecto.sicecuador.otros.usuario.PerfilUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "perfil")
public class Perfil extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @OneToMany(mappedBy = "perfil")
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

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
}
