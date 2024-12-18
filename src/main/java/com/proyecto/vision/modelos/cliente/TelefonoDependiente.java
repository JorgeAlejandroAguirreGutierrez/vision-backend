package com.proyecto.vision.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_telefono_dependiente;

@Entity
@Table(name = tabla_telefono_dependiente)
@Getter
@Setter
@AllArgsConstructor
public class TelefonoDependiente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
