package com.proyecto.vision.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_telefono;

@Entity
@Table(name = tabla_telefono)
@Getter
@Setter
@AllArgsConstructor
public class Telefono extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Telefono(long id){
        super(id);
    }
    public Telefono(){
        super();
        this.codigo = Constantes.vacio;
        this.numero = Constantes.vacio;
    }
}
