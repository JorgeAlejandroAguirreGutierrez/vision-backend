package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "correo_proveedor")
public class CorreoProveedor extends Entidad {
    @Column(name = "email", nullable = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public CorreoProveedor(){
        super();
        this.email = Constantes.vacio;
    }

    public CorreoProveedor(long id) {
        super(id);
    }

    public CorreoProveedor(String codigo, String email, Proveedor proveedor) {
        super(codigo);
        this.email=email;
        this.proveedor=proveedor;
    }

    public String getEmail() {
        return email;
    }

    @JsonBackReference
    public Proveedor getProveedor() {
        return proveedor;
    }
}
