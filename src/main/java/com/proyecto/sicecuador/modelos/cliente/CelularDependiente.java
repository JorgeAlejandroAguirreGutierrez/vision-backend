package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "celular_dependiente")
public class CelularDependiente extends Entidad {
    @NotNull
    @NotBlank
    @Column(name = "numero")
    private String numero;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "dependiente_id")
    private Dependiente dependiente;

    public CelularDependiente(){
        super();
    }


    public CelularDependiente(long id) {
        super(id);
    }

    public CelularDependiente(String codigo, String numero, Dependiente dependiente) {
        super(codigo);
        this.numero=numero;
        this.dependiente=dependiente;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Dependiente getDependiente() {
        return dependiente;
    }
}
