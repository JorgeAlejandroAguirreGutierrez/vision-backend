package com.proyecto.sicecuador.modelos.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.comprobante.TipoComprobanteUtil;

import javax.persistence.*;

@Entity
@Table(name = "tipo_comprobante")
@EntityListeners({TipoComprobanteUtil.class})
public class TipoComprobante extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "nombre_tabla", nullable = true)
    private String nombre_tabla;

    public TipoComprobante(){

    }

    public TipoComprobante(long id){
        super(id);
    }
    public TipoComprobante(String codigo, String descripcion, String nombre_tabla){
        super(codigo);
        this.descripcion=descripcion;
        this.nombre_tabla=nombre_tabla;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre_tabla() {
        return nombre_tabla;
    }
}
