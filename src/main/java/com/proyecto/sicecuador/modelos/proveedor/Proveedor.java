package com.proyecto.sicecuador.modelos.proveedor;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "telefono", nullable = true)
    private String telefono;
    @Column(name = "celular", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
	
    public Proveedor(){
        super();
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.telefono = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.tipoIdentificacion = new TipoIdentificacion();
    }

	public Proveedor(long id){
        super(id);
    }

    public Proveedor(String codigo, TipoIdentificacion tipoIdentificacion, String identificacion,
    				String razonSocial, String nombreComercial, String estado,
    				String direccion, String telefono, String celular, String correo){
        super(codigo);
    	this.tipoIdentificacion = tipoIdentificacion;
    	this.identificacion = identificacion;
    	this.razonSocial = razonSocial;
    	this.nombreComercial = nombreComercial;
    	this.estado = estado;
    	this.direccion = direccion;
    	this.telefono = telefono;
        this.celular = celular;
    	this.correo = correo;
 
    }
    
    public Proveedor(List<String> datos) {
    	super(null);
        tipoIdentificacion=datos.get(0)== null ? null: new TipoIdentificacion(Long.parseLong(datos.get(0)));
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        nombreComercial=datos.get(3)== null ? null: datos.get(3);
        estado=datos.get(4)== null ? null: datos.get(4);
        direccion= datos.get(6)== null ? null: datos.get(6);
        telefono= datos.get(7)== null ? null: datos.get(7);
        celular = datos.get(8)== null ? null: datos.get(8);
        correo = datos.get(9)== null ? null: datos.get(9);
        
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
		return razonSocial;
	}
    
    public String getNombreComercial() {
    	return nombreComercial;
    }

    public String getEstado() {
        return estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
    
    public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void setCorreo(String correo) {
		this.correo = correo;
	}

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
    }
    
}
