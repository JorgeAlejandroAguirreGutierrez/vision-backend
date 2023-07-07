package com.proyecto.vision.modelos.usuario;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_usuario;

@Entity
@Table(name = tabla_usuario)
@Getter
@Setter
@AllArgsConstructor
public class Usuario extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
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
    @Transient
    private String avatar64;
    @Lob
    @Column(name = "avatar", nullable = true )
    private byte[] avatar;
    @Column(name = "cambiarContrasena", nullable = true)
    private String cambiarContrasena;
    @Column(name = "pregunta", nullable = true)
    private String pregunta;
    @Column(name = "respuesta", nullable = true)
    private String respuesta;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
    public Usuario(long id){
        super(id);
    }
    public Usuario(){
        super();
        this.codigo = Constantes.vacio;
        this.apodo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.telefono = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.contrasena = Constantes.vacio;
        this.confirmarContrasena = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.avatar = null;
        this.cambiarContrasena = Constantes.no;
        this.pregunta = Constantes.vacio;
        this.respuesta = Constantes.vacio;
        this.estado = Constantes.activo;
    }
    public void normalizar(){
        if(this.estacion == null) this.estacion = new Estacion();
        if(this.perfil == null) this.perfil = new Perfil();
    }
}
