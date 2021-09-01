package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "medida")
public class Medida extends Entidad {
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@NotNull
	@NotEmpty
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@JsonProperty("estado")
    @Column(name = "estado", nullable = true)
    private String estado;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("precios")
    @JoinColumn(name = "medida_id", nullable = true)
    private List<Precio> precios;

	public Medida(){
        super();
    }

    public Medida(long id){
        super(id);
    }

    public Medida(String codigo, String tipo, String descripcion, String abreviatura, String estado){
        super(codigo);
        this.tipo=tipo;
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }
    public Medida(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
        abreviatura=datos.get(2)== null ? null: datos.get(2);
        estado=datos.get(3)== null ? null: datos.get(3);
    }
    
    public String getTipo() {
        return tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getAbreviatura() {
        return abreviatura;
    }
    public String getEstado() {
        return estado;
    }
    @JsonManagedReference
    public List<Precio> getPrecios() {
		return precios;
	}   
    
    public void setTipo(String tipo) {
        this.tipo=tipo;
    }
    public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
    public void setEstado(String estado) {
		this.estado = estado;
	}

}
