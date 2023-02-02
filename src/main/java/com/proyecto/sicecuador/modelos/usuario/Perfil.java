package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "perfil")
public class Perfil extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "multiempresa", nullable = true)
    private String multiempresa;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = true)
    private List<Permiso> permisos;

    public Perfil(){
        super();
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.multiempresa = Constantes.no;
        this.estado = Constantes.activo;
        this.permisos = Collections.emptyList();
    }

    public Perfil(long id) {
        super(id);
    }

    public Perfil (String codigo, String descripcion, String abreviatura, String estado, String multiempresa){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
        this.multiempresa=multiempresa;
    }

    public Perfil(List<String>datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        abreviatura=datos.get(1)== null ? null: datos.get(1);
        estado=datos.get(2)== null ? null: datos.get(2);
        multiempresa=datos.get(3)== null ? null: datos.get(3);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    
    public String getEstado() {
		return estado;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public String getMultiempresa() {
		return multiempresa;
	}
    
	@JsonManagedReference
    public List<Permiso> getPermisos() {
        return permisos;
    }

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	public void setMultiempresa(String multiempresa) {
		this.multiempresa = multiempresa;
	}

    public void normalizar(){
        if(this.permisos == null) this.permisos = Collections.emptyList();
    }
	
}
