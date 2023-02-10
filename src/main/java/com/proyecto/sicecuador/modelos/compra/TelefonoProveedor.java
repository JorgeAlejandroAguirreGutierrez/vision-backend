package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "telefono_proveedor")
@Data
@AllArgsConstructor
public class TelefonoProveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
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
}
