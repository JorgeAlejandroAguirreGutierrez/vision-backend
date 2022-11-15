package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "estacion")
public class Estacion extends Entidad {
    
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
	@Column(name = "nompre_pc", nullable = true)
    private String nombrePc;
	@Column(name = "ip", nullable = true)
    private String ip;
	@Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@Column(name = "estado", nullable = true)
	private String estado;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    private List<EstacionUsuario> estacionesUsuarios;    
    
    
	public Estacion() {
		super();
	}
	public Estacion(long id) {
		super(id);
	}
	
	public Estacion(String codigo, String descripcion, String nombrePc, String ip, String abreviatura, String estado, Establecimiento establecimiento) {
		super(codigo);
		this.descripcion=descripcion;
		this.nombrePc=nombrePc;
		this.ip=ip;
		this.abreviatura=abreviatura;		
		this.estado=estado;
		this.establecimiento=establecimiento;
	}
    public Estacion(List<String>datos) {
        descripcion=datos.get(0)== null ? null: datos.get(0);
        nombrePc=datos.get(1)== null ? null: datos.get(1);
        ip=datos.get(2)== null ? null: datos.get(2);
        abreviatura=datos.get(3)== null ? null: datos.get(3);
        estado=datos.get(4)== null ? null: datos.get(4);
        establecimiento=datos.get(5)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(5)));
    }	

    
	public String getDescripcion() {
		return descripcion;
	}
	public String getNombrePc() {
		return nombrePc;
	}
	public String getIp() {
		return ip;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public String getEstado() {
		return estado;
	}
	@JsonBackReference
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setNombrePc(String nombrePc) {
		this.nombrePc = nombrePc;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	
	
}
