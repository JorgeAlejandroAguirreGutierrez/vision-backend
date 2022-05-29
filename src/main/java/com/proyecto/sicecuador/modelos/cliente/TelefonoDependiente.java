package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "telefono_auxiliar")
public class TelefonoDependiente extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "auxiliar_id", nullable = true)
    private Dependiente auxiliar;

    public TelefonoDependiente(){
        super();
    }


    public TelefonoDependiente(long id) {
        super(id);
    }

    public TelefonoDependiente(String codigo, String numero, Dependiente auxiliar) {
        super(codigo);
        this.numero=numero;
        this.auxiliar=auxiliar;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Dependiente getAuxiliar() {
        return auxiliar;
    }
}
