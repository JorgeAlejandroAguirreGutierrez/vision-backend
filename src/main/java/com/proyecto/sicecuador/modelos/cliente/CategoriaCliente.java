package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categoria_cliente")
public class CategoriaCliente extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @NotNull
    @NotBlank
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
