package com.proyecto.sicecuador.modelos.usuario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidad {
	@Column(name = "apodo", nullable = true)
    private String apodo;
	@Column(name = "nombre", nullable = true)
    private String nombre;
	@Column(name = "telefono", nullable = true)
    private String telefono;
	@Column(name = "celular", nullable = true)
    private String celular;
	@Column(name = "correo", nullable = true)
    private String correo;
	@Column(name = "contrasena", nullable = true)
    private String contrasena;
	@Column(name = "confirmar_contrasena", nullable = true)
    private String confirmarContrasena;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "avatar", nullable = true)
    private String avatar;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "cambiarContrasena", nullable = true)
    private String cambiarContrasena;
    @Column(name = "pregunta", nullable = true)
    private String pregunta;
    @Column(name = "respuesta", nullable = true)
    private String respuesta;
    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
    
    public Usuario(){

    }

    public Usuario(long id){
        super(id);
    }
    public Usuario(String codigo, String nombre, String telefono, String celular, String correo, String contrasena, String confirmarContrasena, String identificacion, String avatar,
    		String apodo, String cambiarContrasena, String pregunta, String respuesta, String estado, Perfil perfil, Estacion estacion){
        super(codigo);
        this.apodo=apodo;
        this.nombre=nombre;
        this.telefono = telefono;
        this.celular = celular;
        this.correo=correo;
        this.identificacion=identificacion;
        this.contrasena=contrasena;
        this.confirmarContrasena = confirmarContrasena;
        this.avatar=avatar;
        this.estado=estado;
        this.cambiarContrasena=cambiarContrasena;
        this.pregunta=pregunta;
        this.respuesta=respuesta;
        this.perfil=perfil;
        this.estacion=estacion;
    }

    public Usuario(List<String> datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        correo=datos.get(1)== null ? null: datos.get(1);
        contrasena=datos.get(2)== null ? null: datos.get(2);
        identificacion=datos.get(3)== null ? null: datos.get(3);
        avatar=datos.get(4)== null ? null: datos.get(4);
        estado=datos.get(5)== null ? null: datos.get(5);
        perfil=datos.get(7)== null ? null:new Perfil((long) Double.parseDouble(datos.get(7)));
        apodo=datos.get(8)== null ? null: datos.get(8);
        cambiarContrasena=datos.get(9)== null ? null: datos.get(9);
        pregunta=datos.get(10)== null ? null: datos.get(10);
        respuesta=datos.get(11)== null ? null: datos.get(11);        
        estado=datos.get(12) == null  ? null : datos.get(12);
        perfil=datos.get(13)== null ? null:new Perfil((long) Double.parseDouble(datos.get(13)));
    }
    
    public String getApodo() {
		return apodo;
	}

    public String getNombre() {
        return nombre;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public String getContrasena() {
        return contrasena;
    }
    
    public String getConfirmarContrasena() {
		return confirmarContrasena;
	}

    public String getAvatar() {
        return avatar;
    }

    public String getEstado() {
		return estado;
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
	
	public Estacion getEstacion() {
		return estacion;
	}

    public Perfil getPerfil() {
        return perfil;
    }

	public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEstado(String estado) {
		this.estado = estado;
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
	
	public void setApodo(String apodo) {
		this.apodo = apodo;
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

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
}
