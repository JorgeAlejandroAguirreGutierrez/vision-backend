package com.proyecto.vision.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_correo;

@Entity
@Table(name = tabla_correo)
@Getter
@Setter
@AllArgsConstructor
public class Correo extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Correo(long id){
        super(id);
    }
    public Correo(){
        super();
        this.codigo = Constantes.vacio;
        this.email = Constantes.vacio;
    }
}
