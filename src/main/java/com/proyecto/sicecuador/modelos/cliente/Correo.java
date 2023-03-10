package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_correo;

@Entity
@Table(name = tabla_correo)
@Data
@AllArgsConstructor
public class Correo extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "email", nullable = true)
    private String email;
    @JsonBackReference
    @ManyToOne
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
