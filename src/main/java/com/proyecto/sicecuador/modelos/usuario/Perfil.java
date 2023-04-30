package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_perfil;

@Entity
@Table(name = tabla_perfil)
@Getter
@Setter
@AllArgsConstructor
public class Perfil extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "multiempresa", nullable = true)
    private String multiempresa;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = true)
    private List<Permiso> permisos;

    public Perfil(long id) {
        super(id);
    }
    public Perfil(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.multiempresa = Constantes.no;
        this.estado = Constantes.activo;
        this.permisos = Collections.emptyList();
    }
    public void normalizar(){
        if(this.permisos == null) this.permisos = Collections.emptyList();
    }
	
}
