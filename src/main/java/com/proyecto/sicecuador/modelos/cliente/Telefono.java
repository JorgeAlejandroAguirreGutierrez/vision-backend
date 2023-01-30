package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "telefono")
public class Telefono extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Telefono(){
        super();
        this.numero = Constantes.vacio;
    }

    public Telefono(long id) {
        super(id);
    }

    public Telefono(String codigo, String numero, Cliente cliente){
        super(codigo);
        this.numero=numero;
        this.cliente=cliente;
    }

    public Telefono(List<String>datos) {
        numero=datos.get(0)== null ? null: datos.get(0);
        cliente=datos.get(1)== null ? null:new Cliente((long) Double.parseDouble(datos.get(1)));
    }
    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
