package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "auxiliar")
public class Auxiliar extends Entidad {
    @NotNull
    @NotBlank
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @NotNull
    @Column(name = "eliminado")
    private boolean eliminado;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "auxiliar_id")
    private List<TelefonoAuxiliar> telefonosAuxiliar;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true,  fetch = FetchType.LAZY)
    @JoinColumn(name = "auxiliar_id")
    private List<CelularAuxiliar> celularesAuxiliar;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "auxiliar_id")
    private List<CorreoAuxiliar> correosAuxiliar;

    public Auxiliar(){

    }

    public Auxiliar(long id) {
        super(id);
    }

    public Auxiliar(String codigo, String razonSocial, boolean estado, boolean eliminado, Cliente cliente, Direccion direccion){
        super(codigo);
        this.razonSocial=razonSocial;
        this.estado=estado;
        this.eliminado=eliminado;
        this.direccion=direccion;
        this.cliente=cliente;

    }

    public Auxiliar(String razonSocial, Cliente cliente){
        super("");
        this.razonSocial=razonSocial;
        this.cliente=cliente;
    }

    public Auxiliar(Cliente cliente){
        super("");
        this.cliente=cliente;
    }

    public Auxiliar(List<String>datos){
        this.razonSocial=datos.get(0)== null? null : datos.get(0);
        this.estado=datos.get(1)== null ? null: datos.get(1).equals("S") ? true : false;
        this.eliminado=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        this.direccion=datos.get(3)==null? null: new Direccion((long) Double.parseDouble(datos.get(3)));
        this.cliente=datos.get(4)== null ? null: new Cliente((long) Double.parseDouble(datos.get(4)));
    }

    public String getRazonSocial() {
		return razonSocial;
	}

    public boolean isEliminado() {
        return eliminado;
    }

    public boolean isEstado() {
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
    public List<TelefonoAuxiliar> getTelefonosAuxiliar() {
		return telefonosAuxiliar;
	}
    @JsonManagedReference
    public List<CelularAuxiliar> getCelularesAuxiliar() {
		return celularesAuxiliar;
	}
    @JsonManagedReference
    public List<CorreoAuxiliar> getCorreosAuxiliar() {
		return correosAuxiliar;
	}
}
