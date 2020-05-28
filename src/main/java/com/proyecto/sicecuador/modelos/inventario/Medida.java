package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.MedidaUtil;

import javax.persistence.*;

@Entity
@Table(name = "medida")
@EntityListeners({MedidaUtil.class})
public class Medida extends Entidad {
    @Column(name = "codigo_norma", nullable = true)
    private String codigo_norma;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    public Medida(){
        super();
    }

    public Medida(long id){
        super(id);
    }

    public Medida(String codigo, String codigo_norma, String descripcion){
        super(codigo);
        this.codigo_norma=codigo_norma;
        this.descripcion=descripcion;
    }
    public String getCodigo_norma() {
        return codigo_norma;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
