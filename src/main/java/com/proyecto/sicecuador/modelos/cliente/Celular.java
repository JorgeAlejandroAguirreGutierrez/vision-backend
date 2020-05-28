package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.CelularUtil;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;

import javax.persistence.*;

@Entity
@Table(name = "celular")
//@EntityListeners({CelularUtil.class})
public class Celular extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Celular(){
        super();
    }


    public Celular(long id) {
        super(id);
    }

    public Celular(String codigo, String numero, Cliente cliente) {
        super(codigo);
        this.numero=numero;
        this.cliente=cliente;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
}
