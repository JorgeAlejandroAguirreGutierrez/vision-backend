package com.proyecto.sicecuador.modelos.proveedor;

import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor extends Entidad {
    @NotNull
    @NotEmpty
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @NotNull
    @NotEmpty
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "estado")
    private String estado;
    @Column(name = "eliminado")
    private boolean eliminado;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "telefono", nullable = true)
    private String telefono;
    @Column(name = "correo", nullable = true)
    private String correo;
	
    public Proveedor(){
        super();
    }

	public Proveedor(long id){
        super(id);
    }

    public Proveedor(String codigo, String tipo_identificacion, String identificacion,  
    				String razon_social, String estado, boolean eliminado,
    				String direccion, String telefono, String correo){
        super(codigo);
    	this.tipoIdentificacion = tipo_identificacion;
    	this.identificacion = identificacion;
    	this.razonSocial = razon_social;
    	this.estado = estado;
    	this.eliminado = eliminado;
    	this.direccion = direccion;
    	this.telefono = telefono;
    	this.correo = correo;
 
    }
    
    public Proveedor(List<String> datos) {
    	super(null);
    	tipoIdentificacion=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3);
        eliminado= datos.get(4)== null ? null: datos.get(4).equals("S") ? true : false;
        direccion= datos.get(5)== null ? null: datos.get(5);
        telefono= datos.get(6)== null ? null: datos.get(6);
        correo= datos.get(7)== null ? null: datos.get(7);
        
    }

    public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
		return razonSocial;
	}

    public String getEstado() {
        return estado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public String getCorreo() {
        return correo;
    }
    
    public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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
    
}
