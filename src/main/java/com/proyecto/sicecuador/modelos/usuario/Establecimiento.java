package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
//import com.proyecto.sicecuador.modelos.usuario.TelefonoEstablecimiento;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "establecimiento")
public class Establecimiento extends Entidad {
	@NotNull
    @Column(name = "codigoSRI", nullable = true)
    private String codigoSRI;
	@NotNull
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@NotNull
    @Column(name = "direccion", nullable = true)
    private String direccion;
	@NotNull
    @Column(name = "latitud", nullable = true)
    private int latitud;
	@NotNull
    @Column(name = "longitud", nullable = true)
    private int longitud;	
	@NotNull
    @Column(name = "estado", nullable = true)
    private String estado;
	@NotNull
	@ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<TelefonoEstablecimiento> telefonos;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<CelularEstablecimiento> celulares;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<CorreoEstablecimiento> correos;

    public Establecimiento(){
        super();
    }
    public Establecimiento(long id){
        super(id);
    }

    public Establecimiento(String codigo, String codigoSRI, String descripcion, String direccion, int latitud, int longitud, String estado,  Empresa empresa, Ubicacion ubicacion){
        super(codigo);
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
        this.direccion=direccion;
        this.latitud=latitud;
        this.longitud=longitud;
        this.estado=estado;
        this.empresa=empresa;
        this.ubicacion=ubicacion;
    }
    public Establecimiento(List<String> datos){
        codigoSRI=datos.get(0)== null ? null: datos.get(0);
    	descripcion=datos.get(1)== null ? null: datos.get(1);
    	direccion=datos.get(2)== null ? null: datos.get(2);
    	latitud=datos.get(3)== null ? null: Integer.parseInt(datos.get(3));
    	longitud=datos.get(4)== null ? null: Integer.parseInt(datos.get(4));
        empresa=datos.get(5)== null ? null:new Empresa((long) Double.parseDouble(datos.get(5)));
        ubicacion=datos.get(6)== null ? null:new Ubicacion((long) Double.parseDouble(datos.get(6)));
    }
    
    public String getCodigoSRI() {
		return codigoSRI;
	}
    
    public String getDescripcion() {
		return descripcion;
	}
    
	public int getLatitud() {
		return latitud;
	}
	
	public int getLongitud() {
		return longitud;
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
	public void setLatitud(int latitud) {
		this.latitud = latitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
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
}
