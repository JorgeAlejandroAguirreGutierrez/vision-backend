package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_correo_proveedor;

@Entity
@Table(name = tabla_correo_proveedor)
@Getter
@Setter
@AllArgsConstructor
public class CorreoProveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
