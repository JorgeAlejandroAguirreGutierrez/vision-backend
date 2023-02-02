package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "correo_dependiente")
public class CorreoDependiente extends Entidad {
    @Column(name = "email", nullable = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "dependiente_id", nullable = true)
    private Dependiente dependiente;

    public CorreoDependiente(){
        super();
        this.email = Constantes.vacio;
    }

    public CorreoDependiente(long id) {
        super(id);
    }

    public CorreoDependiente(String codigo, String email, Dependiente dependiente) {
        super(codigo);
        this.email=email;
        this.dependiente=dependiente;
    }

    public String getEmail() {
        return email;
    }

    @JsonBackReference
    public Dependiente getDependiente() {
        return dependiente;
    }
}
