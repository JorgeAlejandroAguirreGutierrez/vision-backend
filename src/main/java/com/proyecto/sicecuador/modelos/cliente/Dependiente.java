package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "dependiente")
public class Dependiente extends Entidad {
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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<TelefonoDependiente> telefonosDependiente;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CelularDependiente> celularesDependiente;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CorreoDependiente> correosDependiente;

    public Dependiente(){
        super();
        this.razonSocial = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.ubicacion = new Ubicacion();
        this.telefonosDependiente = Collections.emptyList();
        this.celularesDependiente = Collections.emptyList();
        this.correosDependiente = Collections.emptyList();

    }

    public Dependiente(long id) {
        super(id);
    }

    public Dependiente(String codigo, String razonSocial, String direccion, Ubicacion ubicacion, Cliente cliente){
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
    public List<TelefonoDependiente> getTelefonosDependiente() {
		return telefonosDependiente;
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

    public void normalizar(){
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.telefonosDependiente == null) this.telefonosDependiente = Collections.emptyList();
        if(this.celularesDependiente == null) this.celularesDependiente = Collections.emptyList();
        if(this.correosDependiente == null) this.correosDependiente = Collections.emptyList();
    }
}
