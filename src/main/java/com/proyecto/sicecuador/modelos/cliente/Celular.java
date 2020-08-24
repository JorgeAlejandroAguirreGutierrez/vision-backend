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
import java.util.List;

@Entity
@Table(name = "celular")
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
    public Celular(List<String> datos){
        this.numero=datos.get(0)== null? null : datos.get(0);
        this.cliente=datos.get(1)== null ? null: new Cliente((long) Double.parseDouble(datos.get(1)));
    }

    public String getNumero() {
        return numero;
    }
    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
}
