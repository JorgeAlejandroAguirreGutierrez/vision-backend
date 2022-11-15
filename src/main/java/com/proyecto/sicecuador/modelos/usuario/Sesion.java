package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sesion")
public class Sesion extends Entidad {
    @Column(name = "fecha_apertura", nullable = true)
    private Date fechaApertura;
    @Column(name = "fecha_cierre", nullable = true)
    private Date fechaCierre;
    @Column(name = "sesion_ip", nullable = true)
    private String sesionIP;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    
    public Sesion(){

    }

    public Sesion(long id){
        super(id);
    }

    public Sesion(String codigo, Date fechaApertura, Date fechaCierre, String sesionIP, boolean estado, Usuario usuario, Estacion estacion, Empresa empresa){
        super(codigo);
        this.fechaApertura=fechaApertura;
        this.fechaCierre=fechaCierre;
        this.sesionIP=sesionIP;
        this.estado=estado;
        this.usuario=usuario;
        this.estacion=estacion;
        this.empresa=empresa;
    }
    public Sesion(List<String> datos){
        fechaApertura=datos.get(0)== null ? null: new Date(datos.get(0));
        fechaCierre=datos.get(1)== null ? null: new Date(datos.get(1));
        sesionIP=datos.get(2)== null ? null: datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        usuario=datos.get(4)== null ? null:new Usuario((long) Double.parseDouble(datos.get(4)));
        estacion=datos.get(5)== null ? null:new Estacion((long) Double.parseDouble(datos.get(5)));
        empresa=datos.get(6)== null ? null:new Empresa((long) Double.parseDouble(datos.get(6)));
    }
    
    public Date getFechaApertura() {
		return fechaApertura;
	}

    public Date getFechaCierre() {
		return fechaCierre;
	}

    public String getSesionIP() {
		return sesionIP;
	}

    public Usuario getUsuario() {
        return usuario;
    }

    
	public Estacion getEstacion() {
		return estacion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public boolean isEstado() {
        return estado;
    }

    public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
    
    public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

	public void setSesionIP(String sesionIP) {
		this.sesionIP = sesionIP;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
    
    
}
