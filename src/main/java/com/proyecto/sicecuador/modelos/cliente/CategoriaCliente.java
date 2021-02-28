package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.CategoriaClienteUtil;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categoria_cliente")
@EntityListeners({CategoriaClienteUtil.class})
public class CategoriaCliente extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @NotNull
    @NotBlank
    @JsonProperty("abreviatura")
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public CategoriaCliente(){

    }

    public CategoriaCliente(long id) {
        super(id);
    }

    public CategoriaCliente(String codigo, String descripcion, String abreviatura) {
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public CategoriaCliente(List<String> datos){
        this.descripcion=datos.get(0)== null? null : datos.get(0);
        this.abreviatura=datos.get(1)== null? null : datos.get(1);
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
