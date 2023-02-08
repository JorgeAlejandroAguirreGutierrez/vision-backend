package com.proyecto.sicecuador.modelos.proveedor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;

import javax.persistence.*;

@Entity
@Table(name = "celular_proveedor")
public class CelularProveedor extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public CelularProveedor(){
        super();
        this.numero = Constantes.vacio;
    }

    public CelularProveedor(long id) {
        super(id);
    }

    public CelularProveedor(String codigo, String numero, Proveedor proveedor) {
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
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
