package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.CategoriaClienteUtil;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categoria_cliente")
//@EntityListeners({CategoriaClienteUtil.class})
public class CategoriaCliente extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @NotNull(message = "Categoria Cliente Abreviatura"+ Constantes.mensaje_validacion_not_null)
    @NotBlank(message = "Categoria Cliente Abreviatura"+Constantes.mensaje_validacion_not_blank)
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
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
