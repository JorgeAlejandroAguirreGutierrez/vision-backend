package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;
import com.proyecto.sicecuador.otros.usuario.PermisoUtil;

import javax.persistence.*;

@Entity
@Table(name = "permiso")
public class Permiso extends Entidad {
    @Column(name = "modulo", nullable = true)
    private String modulo;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "habilitado", nullable = true)
    private boolean habilitado;
    @ManyToOne(cascade = CascadeType.MERGE)
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

    public String getModulo() {
        return modulo;
    }

    public String getOperacion() {
        return operacion;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
