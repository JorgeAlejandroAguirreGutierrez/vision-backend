package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "correo")
//@EntityListeners({CorreoUtil.class})
public class Correo extends Entidad {
	@NotNull
    @NotEmpty
    @JsonProperty("email")
    @Column(name = "email", nullable = false)
    private String email;
    @ManyToOne
    @JsonProperty("cliente")
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Correo(){

    }

    public Correo(long id) {
        super(id);
    }

    public Correo(String codigo, String email, Cliente cliente){
        super(codigo);
        this.email=email;
        this.cliente=cliente;
    }

    public Correo(List<String> datos){
        this.email=datos.get(0)== null? null : datos.get(0);
        this.cliente=datos.get(1)== null ? null: new Cliente((long) Double.parseDouble(datos.get(1)));
    }
    public String getEmail() {
        return email;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
