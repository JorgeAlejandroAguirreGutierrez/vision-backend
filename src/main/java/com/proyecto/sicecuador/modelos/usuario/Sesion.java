package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

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
    @Column(name = "ip", nullable = true)
    private String ip;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
    
    public Sesion(){
        super();
        this.fechaApertura = new Date();
        this.fechaCierre = new Date();
        this.ip = Constantes.vacio;
        this.estado = Constantes.activo;
        this.usuario = new Usuario();
    }

    public Sesion(long id){
        super(id);
    }

    public Sesion(String codigo, Date fechaApertura, Date fechaCierre, String sesionIP, String estado, Usuario usuario){
        super(codigo);
        this.fechaApertura=fechaApertura;
        this.fechaCierre=fechaCierre;
        this.ip=sesionIP;
        this.estado=estado;
        this.usuario=usuario;
    }
    public Sesion(List<String> datos){
        fechaApertura=datos.get(0)== null ? null: new Date(datos.get(0));
        fechaCierre=datos.get(1)== null ? null: new Date(datos.get(1));
        ip=datos.get(2)== null ? null: datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3);
        usuario=datos.get(4)== null ? null:new Usuario((long) Double.parseDouble(datos.get(4)));
    }
    
    public Date getFechaApertura() {
		return fechaApertura;
	}

    public Date getFechaCierre() {
		return fechaCierre;
	}

    public String getIp() {
		return ip;
	}

    public Usuario getUsuario() {
        return usuario;
    }

	public String getEstado() {
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

    public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    public void normalizar(){
        if(this.fechaApertura == null) this.fechaApertura = new Date();
        if(this.fechaCierre == null) this.fechaCierre = new Date();
        if(this.usuario == null) this.usuario = new Usuario();
    }
}
