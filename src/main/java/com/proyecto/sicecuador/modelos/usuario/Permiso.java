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
    @Column(name = "opcion", nullable = true)
    private String opcion;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;

    public Permiso(){
        super();
    }
    public Permiso(long id){
        super(id);
    }
    public Permiso(String codigo, String modulo, String operacion, boolean habilitado, String opcion, Perfil perfil) {
        super(codigo);
        this.modulo = modulo;
        this.operacion = operacion;
        this.habilitado = habilitado;
        this.opcion = opcion;
        this.perfil = perfil;
    }

    public Permiso(List<String> datos){
        modulo=datos.get(0)== null ? null: datos.get(0);
        operacion=datos.get(1)== null ? null: datos.get(1);
        habilitado=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        opcion=datos.get(3)== null ? null: datos.get(3);
        perfil=datos.get(4)== null ? null:new Perfil((long) Double.parseDouble(datos.get(4)));
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
    public String getOpcion() {
        return opcion;
    }    
    @JsonBackReference
    public Perfil getPerfil() {
        return perfil;
    }
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
    
}
