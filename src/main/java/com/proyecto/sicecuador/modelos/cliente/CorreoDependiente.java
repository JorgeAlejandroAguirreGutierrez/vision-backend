package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_correo_dependiente;

@Entity
@Table(name = tabla_correo_dependiente)
@Data
@AllArgsConstructor
public class CorreoDependiente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
    @ManyToOne
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
