package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "telefono_proveedor")
public class TelefonoProveedor extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public TelefonoProveedor(){
        super();
        this.numero = Constantes.vacio;
    }

    public TelefonoProveedor(long id) {
        super(id);
    }

    public TelefonoProveedor(String codigo, String numero, Proveedor proveedor) {
        super(codigo);
        this.numero=numero;
        this.proveedor=proveedor;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Proveedor getProveedor() {
        return proveedor;
    }
}
