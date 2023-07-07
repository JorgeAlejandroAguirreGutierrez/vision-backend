package com.proyecto.vision.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_correo_dependiente;

@Entity
@Table(name = tabla_correo_dependiente)
@Getter
@Setter
@AllArgsConstructor
public class CorreoDependiente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id", nullable = true)
    private Dependiente dependiente;

    public CorreoDependiente(long id){
        super(id);
    }
    public CorreoDependiente(){
        super();
        this.codigo = Constantes.vacio;
        this.email = Constantes.vacio;
    }
}
