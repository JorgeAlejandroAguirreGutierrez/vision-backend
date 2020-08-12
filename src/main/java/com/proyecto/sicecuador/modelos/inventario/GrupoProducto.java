package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.GrupoProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo_producto")
@EntityListeners({GrupoProductoUtil.class})
public class GrupoProducto extends Entidad {
    @Column(name = "grupo", nullable = true)
    private String grupo;
    @Column(name = "subgrupo", nullable = true)
    private String subgrupo;
    @Column(name = "categoria", nullable = true)
    private String categoria;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "grupo_producto_id")
    private List<LineaProducto> lineas_productos;

    public GrupoProducto(){

    }

    public GrupoProducto(long id){
        super(id);
    }

    public GrupoProducto(String codigo, String grupo, String subgrupo, String categoria){
        super(codigo);
        this.grupo=grupo;
        this.subgrupo=subgrupo;
        this.categoria=categoria;
    }

    public GrupoProducto(List<String>datos){

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

    public String getCategoria() {
        return categoria;
    }

    @JsonManagedReference
    public List<LineaProducto> getLineas_productos() {
        return lineas_productos;
    }
}
