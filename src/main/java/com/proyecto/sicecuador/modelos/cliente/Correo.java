package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;
import com.proyecto.sicecuador.otros.cliente.CorreoUtil;

import javax.persistence.*;
@Entity
@Table(name = "correo")
//@EntityListeners({CorreoUtil.class})
public class Correo extends Entidad {
    @Column(name = "email", nullable = true)
    private String email;
    @ManyToOne(cascade = CascadeType.MERGE)
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
    public String getEmail() {
        return email;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }

}
