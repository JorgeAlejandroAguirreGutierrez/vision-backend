package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "correo_proveedor")
@Data
@AllArgsConstructor
public class CorreoProveedor extends Entidad {

    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
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
}
