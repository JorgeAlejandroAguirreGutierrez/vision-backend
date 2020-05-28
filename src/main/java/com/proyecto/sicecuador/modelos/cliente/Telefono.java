package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.TelefonoUtil;

import javax.persistence.*;
@Entity
@Table(name = "telefono")
//@EntityListeners({TelefonoUtil.class})
public class Telefono extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Telefono(){
        super();
    }

    public Telefono(long id) {
        super(id);
    }

    public Telefono(String codigo, String numero, Cliente cliente){
        super(codigo);
        this.numero=numero;
        this.cliente=cliente;
    }
    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    @JsonBackReference
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
