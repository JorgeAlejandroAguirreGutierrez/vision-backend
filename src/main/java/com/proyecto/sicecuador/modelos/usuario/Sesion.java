package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.usuario.SesionUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sesion")
@EntityListeners({SesionUtil.class})
public class Sesion extends Entidad {
	@JsonProperty("fecha_apertura")
    @Column(name = "fecha_apertura", nullable = true)
    private Date fechaApertura;
	@JsonProperty("fecha_cierre")
    @Column(name = "fecha_cierre", nullable = true)
    private Date fechaCierre;
	@JsonProperty("nombre_pc")
    @Column(name = "nombre_pc", nullable = true)
    private String nombrePC;
	@JsonProperty("sesion_ip")
    @Column(name = "sesion_ip", nullable = true)
    private String sesionIP;
	@JsonProperty("activa")
    @Column(name = "activa", nullable = true)
    private boolean activa;
    @ManyToOne
    @JsonProperty("usuario")
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    public Sesion(){

    }

    public Sesion(long id){
        super(id);
    }

    public Sesion(String codigo, Date fechaApertura, Date fechaCierre, String nombrePC, String sesionIP, boolean activa, Usuario usuario){
        super(codigo);
        this.fechaApertura=fechaApertura;
        this.fechaCierre=fechaCierre;
        this.nombrePC=nombrePC;
        this.sesionIP=sesionIP;
        this.activa=activa;
        this.usuario=usuario;
    }
    public Sesion(List<String> datos){
        fechaApertura=datos.get(0)== null ? null: new Date(datos.get(0));
        fechaCierre=datos.get(1)== null ? null: new Date(datos.get(1));
        nombrePC=datos.get(2)== null ? null: datos.get(2);
        sesionIP=datos.get(3)== null ? null: datos.get(3);
        activa=datos.get(4)== null ? null: datos.get(4).equals("S") ? true : false;
        usuario=datos.get(5)== null ? null:new Usuario((long) Double.parseDouble(datos.get(5)));
    }
    
    public Date getFechaApertura() {
		return fechaApertura;
	}

    public Date getFechaCierre() {
		return fechaCierre;
	}

    public String getNombrePC() {
		return nombrePC;
	}

    public String getSesionIP() {
		return sesionIP;
	}

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isActiva() {
        return activa;
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

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
