package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "celular_auxiliar")
public class CelularAuxiliar extends Entidad {
    @NotNull
    @NotBlank
    @Column(name = "numero")
    private String numero;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "auxiliar_id")
    private Auxiliar auxiliar;

    public CelularAuxiliar(){
        super();
    }


    public CelularAuxiliar(long id) {
        super(id);
    }

    public CelularAuxiliar(String codigo, String numero, Auxiliar auxiliar) {
        super(codigo);
        this.numero=numero;
        this.auxiliar=auxiliar;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Auxiliar getAuxiliar() {
        return auxiliar;
    }
}
