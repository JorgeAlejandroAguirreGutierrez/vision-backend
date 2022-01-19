package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "telefono_auxiliar")
public class TelefonoAuxiliar extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "auxiliar_id", nullable = true)
    private Auxiliar auxiliar;

    public TelefonoAuxiliar(){
        super();
    }


    public TelefonoAuxiliar(long id) {
        super(id);
    }

    public TelefonoAuxiliar(String codigo, String numero, Auxiliar auxiliar) {
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
