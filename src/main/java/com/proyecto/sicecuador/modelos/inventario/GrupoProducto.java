package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.GrupoProductoUtil;

import javax.persistence.*;

@Entity
@Table(name = "grupo_producto")
@EntityListeners({GrupoProductoUtil.class})
public class GrupoProducto extends Entidad {
    @Column(name = "grupo", nullable = true)
    private String grupo;
    @Column(name = "subgrupo", nullable = true)
    private String subgrupo;

    public GrupoProducto(){

    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String grupo, String subgrupo){
        super(codigo);
        this.grupo=grupo;
        this.subgrupo=subgrupo;
    }
    public String getGrupo() {
        return grupo;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
