package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "establecimiento")
public class Establecimiento extends Entidad {
    @Column(name = "codigoSRI", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<TelefonoEstablecimiento> telefonos;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<CelularEstablecimiento> celulares;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<CorreoEstablecimiento> correos;

    public Establecimiento(){
        super();
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.latitudgeo = Constantes.vacio;
        this.longitudgeo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.ubicacion = new Ubicacion();
        this.empresa = new Empresa();
        this.telefonos = Collections.emptyList();
        this.celulares = Collections.emptyList();
        this.correos = Collections.emptyList();

    }
    public Establecimiento(long id){
        super(id);
    }

    public Establecimiento(String codigo, String codigoSRI, String descripcion, String direccion, String latitudgeo, String longitudgeo, String estado,  Empresa empresa, Ubicacion ubicacion){
        super(codigo);
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
        this.direccion=direccion;
        this.latitudgeo=latitudgeo;
        this.longitudgeo=longitudgeo;
        this.estado=estado;
        this.empresa=empresa;
        this.ubicacion=ubicacion;
    }
    public Establecimiento(List<String> datos){
        codigoSRI=datos.get(0)== null ? null: datos.get(0);
    	descripcion=datos.get(1)== null ? null: datos.get(1);
    	direccion=datos.get(2)== null ? null: datos.get(2);
    	latitudgeo=datos.get(3)== null ? null: datos.get(3);
    	longitudgeo=datos.get(4)== null ? null: datos.get(4);
        empresa=datos.get(5)== null ? null:new Empresa((long) Double.parseDouble(datos.get(5)));
        ubicacion=datos.get(6)== null ? null:new Ubicacion((long) Double.parseDouble(datos.get(6)));
    }
    
    public String getCodigoSRI() {
		return codigoSRI;
	}
    
    public String getDescripcion() {
		return descripcion;
	}

    public String getLatitudgeo() {
        return latitudgeo;
    }

    public String getLongitudgeo() {
        return longitudgeo;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public String getEstado() {
        return estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    @JsonManagedReference
    public List<TelefonoEstablecimiento> getTelefonos() {
		return telefonos;
	}    
    @JsonManagedReference
    public List<CelularEstablecimiento> getCelulares() {
		return celulares;
	}    
    @JsonManagedReference
    public List<CorreoEstablecimiento> getCorreos() {
		return correos;
	}
    
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

    public void setLatitudgeo(String latitudgeo) {
        this.latitudgeo = latitudgeo;
    }

    public void setLongitudgeo(String longitudgeo) {
        this.longitudgeo = longitudgeo;
    }

    public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	public void setTelefonos(List<TelefonoEstablecimiento> telefonos) {
		this.telefonos = telefonos;
	}
	public void setCelulares(List<CelularEstablecimiento> celulares) {
		this.celulares = celulares;
	}
	public void setCorreos(List<CorreoEstablecimiento> correos) {
		this.correos = correos;
	}

    public void normalizar(){
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.empresa == null) this.empresa = new Empresa();
        if(this.telefonos == null) this.telefonos = Collections.emptyList();
        if(this.celulares == null) this.celulares = Collections.emptyList();
        if(this.correos == null) this.correos = Collections.emptyList();
    }
}
