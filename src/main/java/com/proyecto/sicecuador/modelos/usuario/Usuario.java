package com.proyecto.sicecuador.modelos.usuario;
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
	@ManyToOne
    @JoinColumn(name = "punto_venta_id", nullable = true)
    private PuntoVenta puntoVenta;
	@ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
	@ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

    public Usuario(){

    }

    public Usuario(long id){
        super(id);
    }

    public Usuario(String codigo, String nombre, String correo, String contrasena, String identificacion, String avatar, String activo, PuntoVenta puntoVenta, Perfil perfil, Empresa empresa){
        super(codigo);
        this.nombre=nombre;
        this.correo=correo;
        this.identificacion=identificacion;
        this.contrasena=contrasena;
        this.avatar=avatar;
        this.activo=activo;
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
        puntoVenta=datos.get(6)== null ? null:new PuntoVenta((long) Double.parseDouble(datos.get(6)));
        perfil=datos.get(7)== null ? null:new Perfil((long) Double.parseDouble(datos.get(7)));
        empresa=datos.get(8)== null ? null:new Empresa((long) Double.parseDouble(datos.get(8)));
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

    public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

    public Perfil getPerfil() {
        return perfil;
    }
        
    public Empresa getEmpresa() {
		return empresa;
	}

	public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setActivo(String activo) {
		this.activo = activo;
	}
}
