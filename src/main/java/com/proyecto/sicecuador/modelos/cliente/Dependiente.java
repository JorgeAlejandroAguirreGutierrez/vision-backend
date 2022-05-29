package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;

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
    @NotNull
    @Column(name = "estado")
    private String estado;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
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

    public Dependiente(String codigo, String razonSocial, String estado, Cliente cliente, Direccion direccion){
        super(codigo);
        this.razonSocial=razonSocial;
        this.estado=estado;
        this.direccion=direccion;
        this.cliente=cliente;

    }

    public Dependiente(String razonSocial, Cliente cliente){
        super("");
        this.razonSocial=razonSocial;
        this.cliente=cliente;
    }

    public Dependiente(Cliente cliente){
        super("");
        this.cliente=cliente;
    }

    public Dependiente(List<String>datos){
        this.razonSocial=datos.get(0)== null? null : datos.get(0);
        this.estado=datos.get(1)== null ? null: datos.get(1);
        this.direccion=datos.get(3)==null? null: new Direccion((long) Double.parseDouble(datos.get(3)));
        this.cliente=datos.get(4)== null ? null: new Cliente((long) Double.parseDouble(datos.get(4)));
    }

    public String getRazonSocial() {
		return razonSocial;
	}

    public String isEstado() {
        return estado;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }

    public Direccion getDireccion() {
        return direccion;
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
}
