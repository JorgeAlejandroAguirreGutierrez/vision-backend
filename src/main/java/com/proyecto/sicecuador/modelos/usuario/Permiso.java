package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permiso")
public class Permiso extends Entidad {
    @Column(name = "modulo", nullable = true)
    private String modulo;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "habilitado", nullable = true)
    private boolean habilitado;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;

    public Permiso(){
        super();
    }
    public Permiso(long id){
        super(id);
    }
    public Permiso(String codigo, String modulo, String operacion, boolean habilitado, String estado, Perfil perfil) {
        super(codigo);
        this.modulo = modulo;
        this.operacion = operacion;
        this.habilitado = habilitado;
        this.estado=estado;
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
    
    public String getEstado() {
		return estado;
	}
    
    @JsonBackReference
    public Perfil getPerfil() {
        return perfil;
    }
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
