package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_celular_proveedor;

@Entity
@Table(name = tabla_celular_proveedor)
@Getter
@Setter
@AllArgsConstructor
public class CelularProveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public CelularProveedor(){
        super();
        this.numero = Constantes.vacio;
    }

    public CelularProveedor(long id) {
        super(id);
    }
}
