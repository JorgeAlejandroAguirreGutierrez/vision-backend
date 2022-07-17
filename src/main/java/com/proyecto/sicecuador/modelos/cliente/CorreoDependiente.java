package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "correo_auxiliar")
public class CorreoDependiente extends Entidad {
    @Column(name = "email", nullable = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "auxiliar_id", nullable = true)
    private Dependiente auxiliar;

    public CorreoDependiente(){
        super();
    }


    public CorreoDependiente(long id) {
        super(id);
    }

    public CorreoDependiente(String codigo, String email, Dependiente auxiliar) {
        super(codigo);
        this.email=email;
        this.auxiliar=auxiliar;
    }

    public String getEmail() {
        return email;
    }

    @JsonBackReference
    public Dependiente getAuxiliar() {
        return auxiliar;
    }
}
