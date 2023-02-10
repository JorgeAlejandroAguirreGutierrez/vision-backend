package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "telefono_dependiente")
@Data
@AllArgsConstructor
public class TelefonoDependiente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "dependiente_id", nullable = true)
    private Dependiente dependiente;

    public TelefonoDependiente(long id){
        super(id);
    }
    public TelefonoDependiente(){
        super();
        this.codigo = Constantes.vacio;
        this.numero = Constantes.vacio;
    }
}
