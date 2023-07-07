package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_telefono_proveedor;

@Entity
@Table(name = tabla_telefono_proveedor)
@Getter
@Setter
@AllArgsConstructor
public class TelefonoProveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
