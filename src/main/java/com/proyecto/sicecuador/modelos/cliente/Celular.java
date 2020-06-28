package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.CelularUtil;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "celular")
//@EntityListeners({CelularUtil.class})
public class Celular extends Entidad {
    @NotNull(message = "Celular - Numero"+ Constantes.mensaje_validacion_not_null)
    @NotEmpty(message = "Celular - Numero"+Constantes.mensaje_validacion_not_blank)
    @Column(name = "numero", nullable = true)
    private String numero;
    @NotNull(message = "Celular - Cliente"+ Constantes.mensaje_validacion_not_null)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public Celular(){
        super();
    }


    public Celular(long id) {
        super(id);
    }

    public Celular(String codigo, String numero, Cliente cliente) {
        super(codigo);
        this.numero=numero;
        this.cliente=cliente;
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
}
