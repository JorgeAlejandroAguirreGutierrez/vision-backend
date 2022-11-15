package com.proyecto.sicecuador.modelos.usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidad {
	@Column(name = "nombre", nullable = true)
    private String nombre;
	@Column(name = "correo", nullable = true)
    private String correo;
	@Column(name = "contrasena", nullable = true)
    private String contrasena;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "avatar", nullable = true)
    private String avatar;
    @Column(name = "activo", nullable = true)
    private String activo;
    @Column(name = "cambiarContrasena", nullable = true)
    private String cambiarContrasena;
    @Column(name = "pregunta", nullable = true)
    private String pregunta;
    @Column(name = "respuesta", nullable = true)
    private String respuesta;
	@ManyToOne
    @JoinColumn(name = "punto_venta_id", nullable = true)
    private PuntoVenta puntoVenta;
	@ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
	@ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private List<EstacionUsuario> estacionesUsuarios;    

    public Usuario(){

    }

    public Usuario(long id){
        super(id);
    }

    public Usuario(String codigo, String nombre, String correo, String contrasena, String identificacion, String avatar, String activo, 
    		String cambiarContrasena, String pregunta, String respuesta, PuntoVenta puntoVenta, Perfil perfil, Empresa empresa){
        super(codigo);
        this.nombre=nombre;
        this.correo=correo;
        this.identificacion=identificacion;
        this.contrasena=contrasena;
        this.avatar=avatar;
        this.activo=activo;
        this.cambiarContrasena=cambiarContrasena;
        this.pregunta=pregunta;
        this.respuesta=respuesta;
        this.puntoVenta=puntoVenta;
        this.perfil=perfil;
        this.empresa=empresa;
    }

    public Usuario(List<String> datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        correo=datos.get(1)== null ? null: datos.get(1);
        contrasena=datos.get(2)== null ? null: datos.get(2);
        identificacion=datos.get(3)== null ? null: datos.get(3);
        avatar=datos.get(4)== null ? null: datos.get(4);
        activo=datos.get(5)== null ? null: datos.get(5);
        cambiarContrasena=datos.get(6)== null ? null: datos.get(6);
        pregunta=datos.get(7)== null ? null: datos.get(7);
        respuesta=datos.get(8)== null ? null: datos.get(8);        
        puntoVenta=datos.get(9)== null ? null:new PuntoVenta((long) Double.parseDouble(datos.get(9)));
        perfil=datos.get(10)== null ? null:new Perfil((long) Double.parseDouble(datos.get(10)));
        empresa=datos.get(11)== null ? null:new Empresa((long) Double.parseDouble(datos.get(11)));
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getActivo() {
		return activo;
	}

    public String getCambiarContrasena() {
		return cambiarContrasena;
	}

	public String getPregunta() {
		return pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

    public Perfil getPerfil() {
        return perfil;
    }
        
    public Empresa getEmpresa() {
		return empresa;
	}

    @JsonManagedReference
	public List<EstacionUsuario> getEstacionesUsuarios() {
		return estacionesUsuarios;
	}

	public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setActivo(String activo) {
		this.activo = activo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setCambiarContrasena(String cambiarContrasena) {
		this.cambiarContrasena = cambiarContrasena;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEstacionesUsuarios(List<EstacionUsuario> estacionesUsuarios) {
		this.estacionesUsuarios = estacionesUsuarios;
	}
    
    
}
