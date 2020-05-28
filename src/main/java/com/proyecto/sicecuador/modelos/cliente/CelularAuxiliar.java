package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "celular_auxiliar")
public class CelularAuxiliar extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "auxiliar_id", nullable = true)
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
