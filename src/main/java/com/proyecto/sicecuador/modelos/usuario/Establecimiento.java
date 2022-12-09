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
    @Column(name = "codigoSri", nullable = true)
    private String codigoSri;
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
    private List<TelefonoEstablecimiento> telefonosEstablecimiento;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<CelularEstablecimiento> celularesEstablecimiento;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<CorreoEstablecimiento> correosEstablecimiento;    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private List<Estacion> estaciones;    

    public Establecimiento(){
        super();
    }
    public Establecimiento(long id){
        super(id);
    }

    public Establecimiento(String codigo, String codigoSri, String descripcion, String direccion, int latitud, int longitud, String estado,  Empresa empresa, Ubicacion ubicacion){
        super(codigo);
        this.codigoSri=codigoSri;
        this.descripcion=descripcion;
        this.direccion=direccion;
        this.latitud=latitud;
        this.longitud=longitud;
        this.estado=estado;
        this.empresa=empresa;
        this.ubicacion=ubicacion;
    }
    public Establecimiento(List<String> datos){
        codigoSri=datos.get(0)== null ? null: datos.get(0);
    	descripcion=datos.get(1)== null ? null: datos.get(1);
    	direccion=datos.get(2)== null ? null: datos.get(2);
    	latitud=datos.get(3)== null ? null: Integer.parseInt(datos.get(3));
    	longitud=datos.get(4)== null ? null: Integer.parseInt(datos.get(4));
        empresa=datos.get(5)== null ? null:new Empresa((long) Double.parseDouble(datos.get(5)));
        ubicacion=datos.get(6)== null ? null:new Ubicacion((long) Double.parseDouble(datos.get(6)));
    }
    
    public String getCodigoSri() {
        return codigoSri;
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
    public List<TelefonoEstablecimiento> getTelefonosEstablecimiento() {
		return telefonosEstablecimiento;
	}    
    @JsonManagedReference
    public List<CelularEstablecimiento> getCelularesEstablecimiento() {
		return celularesEstablecimiento;
	}    
    @JsonManagedReference
    public List<CorreoEstablecimiento> getCorreosEstablecimiento() {
		return correosEstablecimiento;
	}    
    @JsonManagedReference
    public List<Estacion> getEstaciones() {
		return estaciones;
	}
	public void setCodigoSri(String codigoSri) {
		this.codigoSri = codigoSri;
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
	public void setTelefonosEstablecimiento(List<TelefonoEstablecimiento> telefonosEstablecimiento) {
		this.telefonosEstablecimiento = telefonosEstablecimiento;
	}
	public void setCelularesEstablecimiento(List<CelularEstablecimiento> celularesEstablecimiento) {
		this.celularesEstablecimiento = celularesEstablecimiento;
	}
	public void setCorreosEstablecimiento(List<CorreoEstablecimiento> correosEstablecimiento) {
		this.correosEstablecimiento = correosEstablecimiento;
	}
	public void setEstaciones(List<Estacion> estaciones) {
		this.estaciones = estaciones;
	}
}
