package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "telefono_dependiente")
public class TelefonoDependiente extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "dependiente_id", nullable = true)
    private Dependiente dependiente;

    public TelefonoDependiente(){
        super();
        this.numero = Constantes.vacio;
    }

    public TelefonoDependiente(long id) {
        super(id);
    }

    public TelefonoDependiente(String codigo, String numero, Dependiente dependiente) {
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
