package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "dependiente")
public class Dependiente extends Entidad {
    @NotNull
    @NotBlank
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<TelefonoDependiente> telefonosAuxiliar;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true,  fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CelularDependiente> celularesDependiente;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CorreoDependiente> correosDependiente;

    public Dependiente(){

    }

    public Dependiente(long id) {
        super(id);
    }

    public Dependiente(String codigo, String razonSocial, String estado, String direccion, Ubicacion ubicacion, Cliente cliente){
        super(codigo);
        this.razonSocial=razonSocial;
        this.direccion=direccion;
        this.ubicacion=ubicacion;
        this.cliente=cliente;

    }

    public Dependiente(String razonSocial){
        super();
        this.razonSocial=razonSocial;
    }

    public Dependiente(Cliente cliente){
        super();
        this.cliente=cliente;
    }

    public Dependiente(List<String>datos){
        this.razonSocial=datos.get(0)== null? null : datos.get(0);
        this.direccion=datos.get(1)==null? null: datos.get(1);
        this.ubicacion=datos.get(3)== null ? null: new Ubicacion(Long.parseLong(datos.get(3)));
        this.cliente=datos.get(4)== null ? null: new Cliente(Long.parseLong(datos.get(4)));
    }

    public String getRazonSocial() {
		return razonSocial;
	}
    
    public String getDireccion() {
		return direccion;
	}
    
    public Ubicacion getUbicacion() {
		return ubicacion;
	}

    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
    
    @JsonManagedReference
    public List<TelefonoDependiente> getTelefonosAuxiliar() {
		return telefonosAuxiliar;
	}
    @JsonManagedReference
    public List<CelularDependiente> getCelularesDependiente() {
		return celularesDependiente;
	}
    @JsonManagedReference
    public List<CorreoDependiente> getCorreosDependiente() {
		return correosDependiente;
	}
    
    public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
}
