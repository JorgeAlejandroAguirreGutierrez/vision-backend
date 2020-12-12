package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "correo_auxiliar")
public class CorreoAuxiliar extends Entidad {
    @Column(name = "email", nullable = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "auxiliar_id", nullable = true)
    private Auxiliar auxiliar;

    public CorreoAuxiliar(){
        super();
    }


    public CorreoAuxiliar(long id) {
        super(id);
    }

    public CorreoAuxiliar(String codigo, String email, Auxiliar auxiliar) {
        super(codigo);
        this.email=email;
        this.auxiliar=auxiliar;
    }

    public String getEmail() {
        return email;
    }

    @JsonBackReference
    public Auxiliar getAuxiliar() {
        return auxiliar;
    }
}
