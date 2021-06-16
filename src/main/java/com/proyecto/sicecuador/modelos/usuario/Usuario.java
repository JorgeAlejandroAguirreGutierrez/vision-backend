package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidad {
	@JsonProperty("nombre")
	@Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("correo")
	@Column(name = "correo", nullable = true)
    private String correo;
	@JsonProperty("contrasena")
	@Column(name = "contrasena", nullable = true)
    private String contrasena;
	@JsonProperty("identificacion")
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
	@JsonProperty("avatar")
    @Column(name = "avatar", nullable = true)
    private String avatar;
	@JsonProperty("activo")
    @Column(name = "activo", nullable = true)
    private boolean activo;
	@JsonProperty("punto_venta")
	@ManyToOne
    @JoinColumn(name = "punto_venta_id", nullable = true)
    private PuntoVenta puntoVenta;
	@JsonProperty("perfil")
	@ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;

    public Usuario(){

    }

    public Usuario(long id){
        super(id);
    }

    public Usuario(String codigo, String nombre, String correo, String contrasena, String identificacion, String avatar, boolean activo, PuntoVenta puntoVenta, Perfil perfil){
        super(codigo);
        this.nombre=nombre;
        this.correo=correo;
        this.identificacion=identificacion;
        this.contrasena=contrasena;
        this.avatar=avatar;
        this.activo=activo;
        this.puntoVenta=puntoVenta;
        this.perfil=perfil;
    }

    public Usuario(List<String> datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        correo=datos.get(1)== null ? null: datos.get(1);
        contrasena=datos.get(2)== null ? null: datos.get(2);
        identificacion=datos.get(3)== null ? null: datos.get(3);
        avatar=datos.get(4)== null ? null: datos.get(4);
        activo=datos.get(5)== null ? null: datos.get(5).equals("S") ? true : false;
        puntoVenta=datos.get(6)== null ? null:new PuntoVenta((long) Double.parseDouble(datos.get(6)));
        perfil=datos.get(7)== null ? null:new Perfil((long) Double.parseDouble(datos.get(7)));
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

    public boolean isActivo() {
        return activo;
    }

    public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

    public Perfil getPerfil() {
        return perfil;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
